package com.limikju.daangn_market.service;

import com.limikju.daangn_market.domain.Chat;
import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.ProductInfoDto;
import com.limikju.daangn_market.domain.enums.MessageType;
import com.limikju.daangn_market.repository.mongo.ChatRepository;
import com.limikju.daangn_market.repository.mybatis.MemberRepository;
import com.limikju.daangn_market.repository.mybatis.ProductRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

  @Mock
  private ChatRepository chatRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ChatService chatService;

  private Member testMember;
  private Chat testChat;
  private ProductInfoDto testProductInfoDto;

  @BeforeEach
  void setUp() {
    testMember = Member.builder()
        .id(1L)
        .email("test@example.com")
        .build();

    testProductInfoDto = ProductInfoDto.builder()
        .id(1L)
        .ownerId(1L)
        .build();

    testChat = Chat.builder()
        .sellerId(2L)
        .productId(1L)
        .senderId(1L)
        .type(MessageType.COMMON_MESSAGE)
        .body("Hello")
        .createdAt(LocalDateTime.now())
        .build();

    // SecurityContext 설정
    User user = new User("test@gmail.com", "password", new ArrayList<>());
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        user, null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  @Test
  @DisplayName("채팅 조회 - 성공")
  void testGetChats() {
    when(memberRepository.findByEmail(any(String.class))).thenReturn(Optional.of(testMember));
    when(chatRepository.findByProductIdAndBuyerId(1L, testMember.getId())).thenReturn(Flux.just(testChat));

    Flux<Chat> chats = chatService.getChats(1L);

    StepVerifier.create(chats)
        .expectNext(testChat)
        .verifyComplete();

    verify(chatRepository, times(1)).findByProductIdAndBuyerId(1L, testMember.getId());
  }

  @Test
  @DisplayName("채팅방 조회 - 성공")
  void testGetChattingRooms() {
    when(memberRepository.findByEmail(any(String.class))).thenReturn(Optional.of(testMember));
    when(chatRepository.aggregateByProductIdAndSenderId(1L, testMember.getId()))
        .thenReturn(Collections.singletonList(testChat));

    List<Chat> chats = chatService.getChattingRooms(1L);

    assertEquals(1, chats.size());
    assertEquals(testChat, chats.get(0));
    verify(chatRepository, times(1)).aggregateByProductIdAndSenderId(1L, testMember.getId());
  }

  @DisplayName("채팅 전송 - 성공")
  @Test
  void testSendMessage() {
    when(productRepository.findById(1L)).thenReturn(Optional.of(testProductInfoDto));
    when(chatRepository.save(any(Chat.class))).thenReturn(Mono.just(testChat));

    Mono<Chat> chatMono = chatService.sendMessage(1L, "Hello");

    StepVerifier.create(chatMono)
        .expectNext(testChat)
        .verifyComplete();

    verify(chatRepository, times(1)).save(any(Chat.class));
  }

  @Test
  @DisplayName("가격 협상 채팅 전송 - 성공")
  void testSendNegotiation() {
    when(productRepository.findById(1L)).thenReturn(Optional.of(testProductInfoDto));
    Chat negotiationChat = Chat.builder()
        .sellerId(2L)
        .productId(1L)
        .senderId(1L)
        .type(MessageType.PRICE_NEGOTIATION)
        .body(10000L)
        .createdAt(LocalDateTime.now())
        .build();
    when(chatRepository.save(any(Chat.class))).thenReturn(Mono.just(negotiationChat));

    Mono<Chat> chatMono = chatService.sendNegotiation(1L, 10000L);

    StepVerifier.create(chatMono)
        .expectNext(negotiationChat)
        .verifyComplete();

    verify(chatRepository, times(1)).save(any(Chat.class));
  }

  @Test
  @DisplayName("채팅 전송 - 실패 상품 없음")
  void testSendMessageWithInvalidProduct() {
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class,
        () -> chatService.sendMessage(1L, "Hello").block());
  }

  @Test
  @DisplayName("가격 협상 채팅 전송 - 실패 상품 없음")
  void testSendNegotiationWithInvalidProduct() {
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class,
        () -> chatService.sendNegotiation(1L, 10000L).block());
  }
}
