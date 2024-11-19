package com.limikju.daangn_market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limikju.daangn_market.domain.Chat;
import com.limikju.daangn_market.domain.enums.MessageType;
import com.limikju.daangn_market.service.ChatService;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(ChatController.class)
class ChatControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private ChatService chatService;

  @MockBean
  private ChatController chatController;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    webTestClient = WebTestClient.bindToController(chatController).build();

    // SecurityContext 설정
    User user = new User("test@gmail.com", "password", new ArrayList<>());
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        user, null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  @Test
  @DisplayName("채팅 조회 - 성공")
  void getChatsTest() throws Exception {
    // given
    Chat chat1 = Chat.builder()
        .id("1")
        .sellerId(1L)
        .buyerId(2L)
        .productId(1L)
        .senderId(2L)
        .type(MessageType.COMMON_MESSAGE)
        .body("Hello!")
        .createdAt(LocalDateTime.now())
        .build();
    Chat chat2 = Chat.builder()
        .id("1")
        .sellerId(1L)
        .buyerId(2L)
        .productId(1L)
        .senderId(1L)
        .type(MessageType.COMMON_MESSAGE)
        .body("Hello!")
        .createdAt(LocalDateTime.now())
        .build();

    // when
    when(chatService.getChats(anyLong())).thenReturn(Flux.just(chat1, chat2));

    // then
    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/api/chat").queryParam("productId", 1).build())
        .accept(MediaType.TEXT_EVENT_STREAM)
        .exchange()
        .expectStatus().isOk();
  }

  @Test
  @DisplayName("채팅 전송 - 성공")
  void sendMessageTest() throws Exception {
    // given
    String message = "New Message!";
    Chat chat = Chat.builder()
        .id("1")
        .sellerId(1L)
        .buyerId(2L)
        .productId(1L)
        .senderId(2L)
        .type(MessageType.COMMON_MESSAGE)
        .body(message)
        .createdAt(LocalDateTime.now())
        .build();

    // when
    when(chatService.sendMessage(anyLong(), anyString())).thenReturn(Mono.just(chat));

    // then
    webTestClient.post()
        .uri(uriBuilder -> uriBuilder.path("/api/chat").queryParam("productId", 1).build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(objectMapper.writeValueAsString(message))
        .exchange()
        .expectStatus().isOk();
  }

  @Test
  @DisplayName("가격 협상 채팅 전송 - 성공")
  void sendNegotiationTest() throws Exception {
    // given
    Chat negotiationChat = Chat.builder()
        .id("1")
        .sellerId(1L)
        .buyerId(2L)
        .productId(1L)
        .senderId(2L)
        .type(MessageType.PRICE_NEGOTIATION)
        .body(15000L)
        .createdAt(LocalDateTime.now())
        .build();

    // when
    when(chatService.sendNegotiation(anyLong(), anyLong())).thenReturn(Mono.just(negotiationChat));

    // then
    webTestClient.post()
        .uri(uriBuilder -> uriBuilder.path("/api/chat/negotiation").queryParam("productId", 1)
            .build())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(15000L)
        .exchange()
        .expectStatus().isOk();
  }
}
