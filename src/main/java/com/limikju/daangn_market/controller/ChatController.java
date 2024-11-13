package com.limikju.daangn_market.controller;

import com.limikju.daangn_market.domain.Chat;
import com.limikju.daangn_market.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

  private final ChatService chatService;

  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Chat> getChats(@RequestParam Long productId) {
    return chatService.getChats(productId)
        .subscribeOn(Schedulers.boundedElastic());
  }

  @PostMapping
  public Mono<Chat> sendMessage(@RequestParam Long productId, @RequestBody String body) {
    return chatService.sendMessage(productId, body);
  }

  @PostMapping("/negotiation")
  public Mono<Chat> sendMessage(@RequestParam Long productId, @RequestBody Long price) {
    return chatService.sendNegotiation(productId, price);
  }
}
