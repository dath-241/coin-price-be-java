package com.javaweb.model.trigger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "spot_price_snooze")
public class SpotPriceSnooze extends SnoozeCondition {

    private boolean onceInDuration;
}

