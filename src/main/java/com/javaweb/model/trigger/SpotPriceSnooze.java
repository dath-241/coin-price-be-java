package com.javaweb.model.trigger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "spot_price_snooze")
public class SpotPriceSnooze extends SnoozeCondition {
    private  boolean oneTime;
    private  boolean onceInDuration;
    private  boolean repeat;
    private  int repeatTimes; // Số lần lặp
    private LocalDateTime snoozeStartTime;
    private  LocalDateTime snoozeEndTime;
    private  LocalDateTime specificTime;
}
