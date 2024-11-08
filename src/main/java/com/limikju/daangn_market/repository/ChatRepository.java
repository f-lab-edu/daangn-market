package com.limikju.daangn_market.repository;

import com.limikju.daangn_market.domain.Chat;
import java.util.List;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

  @Query("{ productId: ?0, buyerId: ?1 }")
  Flux<Chat> findByProductIdAndBuyerId(String productId, String buyerId);

  @Query("[{ $match: { $or: [ { sellerId: ?0, buyerId: ?1 }, { sellerId: ?1, buyerId: ?0 } ] } }, "
      +   "{ sort: { timestamp: -1 } }, "
      +   "{ $group: { _id: \"$product_id\", latestDocument: { $first: \"$$ROOT\" } } }, "
      +   "{ $replaceRoot: { newRoot: \"$latestDocument\" } } ]")
  List<Chat> aggregateByProductIdAndSenderId(String productId, String senderId);
}
