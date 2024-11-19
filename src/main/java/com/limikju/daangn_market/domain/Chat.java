package com.limikju.daangn_market.domain;

import com.limikju.daangn_market.domain.enums.MessageType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "chat")
public class Chat {

  @Id
  private String id;
  private Long sellerId;
  private Long buyerId;
  private Long productId;
  private Long senderId;
  private MessageType type;
  private Object body;
  private LocalDateTime createdAt;
}
