package com.javaweb.helpers.snooze;

import com.javaweb.dto.snooze.SnoozePriceDTO;

import org.springframework.stereotype.Component;

@Component
public class SnoozeMapHelper {

    // Chuyển đổi từ SnoozePriceDTO sang SnoozePrice
    public SnoozePrice mapSnoozePrice(SnoozePriceDTO dto) {
        SnoozePrice snoozePrice = new SnoozePrice();
        snoozePrice.setOneTime(dto.isOneTime());
        snoozePrice.setOnceInDuration(dto.isOnceInDuration());
        snoozePrice.setRepeat(dto.isRepeat());
        snoozePrice.setRepeatTimes(dto.getRepeatTimes());
        snoozePrice.setSnoozeStartTime(dto.getSnoozeStartTime());
        snoozePrice.setSnoozeEndTime(dto.getSnoozeEndTime());
        snoozePrice.setSpecificTime(dto.getSpecificTime());
        return snoozePrice;
    }
}
