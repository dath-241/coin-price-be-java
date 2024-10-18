package com.javaweb.repository;

import com.javaweb.model.trigger.IndicatorTrigger;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IndicatorTriggerRepository extends MongoRepository<IndicatorTrigger, String> {
    IndicatorTrigger findBySymbolAndUsername(String symbol, String username);

    List<IndicatorTrigger> findByUsername(String username);
}
