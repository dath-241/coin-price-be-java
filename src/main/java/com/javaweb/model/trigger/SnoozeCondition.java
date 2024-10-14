package com.javaweb.model.trigger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class SnoozeCondition {
    @Id
    private  boolean oneTime;
    private  boolean onceInDuration;
    private  boolean repeat;
    private  int repeatTimes; // Số lần lặp
    private  LocalDateTime snoozeStartTime;
    private  LocalDateTime snoozeEndTime;
    private  LocalDateTime specificTime;
}
