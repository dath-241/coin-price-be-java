package com.javaweb.service.trigger.CRUD;

import com.javaweb.dto.trigger.IndicatorTriggerDTO;
import com.javaweb.helpers.trigger.TriggerMapHelper;
import com.javaweb.model.trigger.IndicatorTrigger;
import com.javaweb.repository.trigger.IndicatorTriggerRepository;
import com.javaweb.utils.TriggerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndicatorTriggerService {
    @Autowired
    private IndicatorTriggerRepository indicatorTriggerRepository;

    @Autowired
    private TriggerMapHelper triggerMapHelper;

    public String createTrigger(IndicatorTriggerDTO dto, String username) {
        IndicatorTrigger existingTrigger = indicatorTriggerRepository.findBySymbolAndUsername(dto.getSymbol(), username);

        if (existingTrigger != null) {
            indicatorTriggerRepository.delete(existingTrigger);
        }

        IndicatorTrigger newTrigger= triggerMapHelper.mapIndicatorTrigger(dto);
        newTrigger.setUsername(username);

        IndicatorTrigger savedTrigger = indicatorTriggerRepository.save(newTrigger);
        return savedTrigger.getAlert_id();
    }
    public void deleteTrigger(String symbol, String username) {
        IndicatorTrigger trigger = indicatorTriggerRepository.findBySymbolAndUsername(symbol, username);
        if (trigger != null) {
            indicatorTriggerRepository.delete(trigger);
        } else {
            throw new TriggerNotFoundException("Trigger not found for symbol: " + symbol);
        }
    }
}
