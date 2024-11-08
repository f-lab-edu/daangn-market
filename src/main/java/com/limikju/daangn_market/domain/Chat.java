package com.limikju.daangn_market.domain;

import com.limikju.daangn_market.domain.enums.MessageType;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collation = "chat")
public class Chat {

  @Id
  private String id;
  private String sellerId;
  private String buyerId;
  private String productId;
  private String senderId;
  private MessageType type;
  private String body;
  private LocalDateTime createdAt;
}
