package com.javaweb.helpers.snooze;

import com.javaweb.dto.snooze.SnoozePriceDTO;

import com.javaweb.model.trigger.SpotPriceSnooze;
import org.springframework.stereotype.Component;

@Component
public class SnoozeMapHelper {

    // Chuyển đổi từ SnoozePriceDTO sang SnoozePrice
    public SpotPriceSnooze mapSnoozePrice(SnoozePriceDTO dto) {
        SpotPriceSnooze snoozePrice = new SpotPriceSnooze();

        // Chuyển đổi các trường từ DTO sang model SnoozePrice
        snoozePrice.setOneTime(dto.isOneTime()); // Chuyển đổi oneTime từ DTO
        snoozePrice.setOnceInDuration(dto.isOnceInDuration()); // Chuyển đổi onceInDuration từ DTO
        snoozePrice.setRepeat(dto.isRepeat()); // Chuyển đổi repeat từ DTO
        snoozePrice.setRepeatTimes(dto.getRepeatTimes()); // Chuyển đổi repeatTimes từ DTO
        snoozePrice.setSnoozeStartTime(dto.getSnoozeStartTime()); // Chuyển đổi snoozeStartTime từ DTO
        snoozePrice.setSnoozeEndTime(dto.getSnoozeEndTime()); // Chuyển đổi snoozeEndTime từ DTO
        snoozePrice.setSpecificTime(dto.getSpecificTime()); // Chuyển đổi specificTime từ DTO

        return snoozePrice; // Trả về đối tượng SnoozePrice sau khi chuyển đổi
    }
}
