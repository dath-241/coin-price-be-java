package com.javaweb.service.trigger;

import com.javaweb.dto.snooze.SnoozePriceDTO;
import com.javaweb.dto.trigger.SpotPriceTriggerDTO;
import com.javaweb.helpers.snooze.SnoozeMapHelper;
import com.javaweb.helpers.trigger.TriggerMapHelper;
import com.javaweb.model.trigger.SpotPriceSnooze;
import com.javaweb.model.trigger.SpotPriceTrigger;
import com.javaweb.repository.SnoozePriceRepository;
import com.javaweb.repository.SpotPriceTriggerRepository;
import com.javaweb.service.ITriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotPriceTriggerService implements ITriggerService<SpotPriceTriggerDTO> {

    @Autowired
    private SpotPriceTriggerRepository spotPriceTriggerRepository;
    @Autowired
    private SnoozePriceRepository snoozePriceRepository;
    @Autowired
    private TriggerMapHelper triggerMapHelper;

    @Autowired
    private SnoozeMapHelper snoozeMapHelper;
    public void createTrigger(SpotPriceTriggerDTO dto) {
        SpotPriceTrigger trigger = triggerMapHelper.mapSpotPriceTrigger(dto);
        spotPriceTriggerRepository.save(trigger);
    }
    public void createSnooze(SnoozePriceDTO dto) {
        SpotPriceSnooze snooze = snoozeMapHelper.mapSnoozePrice(dto);
        snoozePriceRepository.save(snooze);
    }
}
