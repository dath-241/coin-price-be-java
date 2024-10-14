package com.javaweb.dto.snooze;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@JsonDeserialize(builder = SnoozePriceDTO.Builder.class)
public class SnoozePriceDTO {

    private final boolean oneTime;
    private final boolean onceInDuration;
    private final boolean repeat;
    private final int repeatTimes; // Số lần lặp
    private final LocalDateTime snoozeStartTime;
    private final LocalDateTime snoozeEndTime;
    private final LocalDateTime specificTime;

    // Private constructor để sử dụng với builder
    private SnoozePriceDTO(Builder builder) {
        this.oneTime = builder.oneTime;
        this.onceInDuration = builder.onceInDuration;
        this.repeat = builder.repeat;
        this.repeatTimes = builder.repeatTimes;
        this.snoozeStartTime = builder.snoozeStartTime;
        this.snoozeEndTime = builder.snoozeEndTime;
        this.specificTime = builder.specificTime;
    }

    // Getter methods
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        // Builder class
            private boolean oneTime;
            private boolean onceInDuration;
            private boolean repeat;
            private int repeatTimes;
            private LocalDateTime snoozeStartTime;
            private LocalDateTime snoozeEndTime;
            private LocalDateTime specificTime;

            public Builder() {
                // Default values can be set here if needed
            }

            public Builder oneTime(boolean oneTime) {
                this.oneTime = oneTime;
                return this;
            }

            public Builder onceInDuration(boolean onceInDuration) {
                this.onceInDuration = onceInDuration;
                return this;
            }

            public Builder repeat(boolean repeat) {
                this.repeat = repeat;
                return this;
            }

            public Builder repeatTimes(int repeatTimes) {
                this.repeatTimes = repeatTimes;
                return this;
            }

            public Builder snoozeStartTime(LocalDateTime snoozeStartTime) {
                this.snoozeStartTime = snoozeStartTime;
                return this;
            }

            public Builder snoozeEndTime(LocalDateTime snoozeEndTime) {
                this.snoozeEndTime = snoozeEndTime;
                return this;
            }

            public Builder specificTime(LocalDateTime specificTime) {
                this.specificTime = specificTime;
                return this;
            }

            public SnoozePriceDTO build() {
                return new SnoozePriceDTO(this);

        }
    }
}