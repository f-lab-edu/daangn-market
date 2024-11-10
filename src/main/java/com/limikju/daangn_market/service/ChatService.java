package com.limikju.daangn_market.service;

import com.limikju.daangn_market.domain.Chat;
import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.enums.MessageType;
import com.limikju.daangn_market.repository.ChatRepository;
import com.limikju.daangn_market.repository.MemberRepository;
import com.limikju.daangn_market.repository.ProductRepository;
import com.limikju.daangn_market.util.secutity.SecurityUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatRepository chatRepository;
  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;

  public Flux<Chat> getChats(Long productId) {

    Member buyer = memberRepository.findByEmail(
        SecurityUtil.getLoginUsername()).orElseThrow(()
        -> new IllegalArgumentException("MEMBER_NOT_FOUND"));

    return chatRepository.findByProductIdAndBuyerId(productId, buyer.getId());

  }

  public List<Chat> getChattingRooms(Long productId) {

    Member sender = memberRepository.findByEmail(
        SecurityUtil.getLoginUsername()).orElseThrow(()
        -> new IllegalArgumentException("MEMBER_NOT_FOUND"));

    return chatRepository.aggregateByProductIdAndSenderId(productId, sender.getId());

  }

  public Mono<Chat> sendMessage(Long productId, Long senderId, String body) {

    Chat chat = Chat.builder()
        .sellerId(productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("옳바르지 않은 상품입니다."))
            .getOwnerId())
        .productId(productId)
        .senderId(senderId)
        .type(MessageType.COMMON_MESSAGE)
        .body(body)
        .createdAt(LocalDateTime.now())
        .build();

    return chatRepository.save(chat);
  }

  public Mono<Chat> sendNegotiation(Long productId, Long senderId, Long price) {

    Chat chat = Chat.builder()
        .sellerId(productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("옳바르지 않은 상품입니다."))
            .getOwnerId())
        .productId(productId)
        .senderId(senderId)
        .type(MessageType.PRICE_NEGOTIATION)
        .body(price)
        .createdAt(LocalDateTime.now())
        .build();

    return chatRepository.save(chat);
  }
}
