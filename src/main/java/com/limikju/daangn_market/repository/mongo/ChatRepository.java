package com.limikju.daangn_market.repository.mongo;

import com.limikju.daangn_market.domain.Chat;
import java.util.List;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository("chatRepository")
public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

  @Query("{ productId: ?0, $or: [ { buyerId: ?1 }, { sellerId: ?1 } ] }")
  Flux<Chat> findByProductIdAndBuyerId(Long productId, Long buyerId);

  @Query("[{ $match: { $or: [ { sellerId: ?0, buyerId: ?1 }, { sellerId: ?1, buyerId: ?0 } ] } }, "
      +   "{ sort: { timestamp: -1 } }, "
      +   "{ $group: { _id: \"$product_id\", latestDocument: { $first: \"$$ROOT\" } } }, "
      +   "{ $replaceRoot: { newRoot: \"$latestDocument\" } } ]")
  List<Chat> aggregateByProductIdAndSenderId(Long productId, Long senderId);
}
