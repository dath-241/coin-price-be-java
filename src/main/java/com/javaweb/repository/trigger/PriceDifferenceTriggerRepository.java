package com.javaweb.repository.trigger;

import com.javaweb.model.trigger.PriceDifferenceTrigger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PriceDifferenceTriggerRepository extends MongoRepository<PriceDifferenceTrigger, String> {
    PriceDifferenceTrigger findBySymbolAndUsername(String symbol, String username);
    List<PriceDifferenceTrigger> findByUsername(String username);

    @Query(value = "{}", fields = "{username: 1, symbol: 1}")
    List<PriceDifferenceTrigger> findAllUsernamesWithSymbols();
}
