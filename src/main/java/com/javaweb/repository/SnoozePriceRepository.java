package com.javaweb.repository;

import com.javaweb.model.trigger.SpotPriceTrigger;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SnoozePriceRepository extends MongoRepository<SpotPriceTrigger, String> {

}
