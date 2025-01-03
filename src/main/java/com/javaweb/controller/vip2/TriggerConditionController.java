package com.javaweb.controller.vip2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.dto.FundingIntervalDTO;
import com.javaweb.dto.trigger.*;
import com.javaweb.service.trigger.CRUD.FundingRateTriggerService;
import com.javaweb.service.trigger.CRUD.FuturePriceTriggerService;
import com.javaweb.service.trigger.CRUD.PriceDifferenceTriggerService;
import com.javaweb.service.trigger.CRUD.SpotPriceTriggerService;
import com.javaweb.service.trigger.GetTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.javaweb.service.trigger.CRUD.ListingTriggerService;
import com.javaweb.service.trigger.CRUD.DelistingTriggerService;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vip2")
public class TriggerConditionController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpotPriceTriggerService spotPriceTriggerService;

    @Autowired
    private FuturePriceTriggerService futurePriceTriggerService;

    @Autowired
    private GetTriggerService getTriggerService;

    @Autowired
    private PriceDifferenceTriggerService priceDifferenceTriggerService;

    @Autowired
    private FundingRateTriggerService fundingRateTriggerService;

    @Autowired
    private ListingTriggerService listingTriggerService;

    @Autowired
    private DelistingTriggerService delistingTriggerService;

    @PostMapping("/create")
    public ResponseEntity<?> createTriggerCondition(@RequestParam("triggerType") String triggerType, @RequestBody Map<String, Object> dtoMap, HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            String alertId = "";
            switch (triggerType) {
                case "spot":
                    SpotPriceTriggerDTO spotDTO = objectMapper.convertValue(dtoMap, SpotPriceTriggerDTO.class);
                    alertId = spotPriceTriggerService.createTrigger(spotDTO, username);
                    break;
                case "future":
                    FuturePriceTriggerDTO futureDTO = objectMapper.convertValue(dtoMap, FuturePriceTriggerDTO.class);
                    alertId = futurePriceTriggerService.createTrigger(futureDTO, username);
                    break;
                case "price-difference":
                    PriceDifferenceTriggerDTO priceDTO = objectMapper.convertValue(dtoMap, PriceDifferenceTriggerDTO.class);
                    alertId = priceDifferenceTriggerService.createTrigger(priceDTO, username);
                    break;
                case "funding-rate":
                    FundingRateTriggerDTO fundingDTO = objectMapper.convertValue(dtoMap, FundingRateTriggerDTO.class);
                    alertId = fundingRateTriggerService.createTrigger(fundingDTO, username);
                    break;
                case "listing":
                    ListingDTO listingDTO = objectMapper.convertValue(dtoMap, ListingDTO.class);
                    alertId = listingTriggerService.createTrigger(listingDTO, username);
                    break;
                case "delisting":
                    DelistingDTO delistingDTO = objectMapper.convertValue(dtoMap, DelistingDTO.class);
                    alertId = delistingTriggerService.createTrigger(delistingDTO, username);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Invalid trigger type");
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Alert created successfully");
            response.put("alert_id", alertId);

            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error processing trigger");
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/get/alerts")
    public ResponseEntity<List<Object>> getAllTriggersByUsername(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        List<Object> allTriggers = getTriggerService.findAllTriggersByUsername(username);
        return ResponseEntity.ok(allTriggers);
    }

    @DeleteMapping("/delete/{symbol}")
    public ResponseEntity<?> deleteTriggerCondition(@PathVariable String symbol, @RequestParam("triggerType") String triggerType, HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");

            switch (triggerType) {
                case "spot":
                    spotPriceTriggerService.deleteTrigger(symbol, username);
                    break;
                case "future":
                    futurePriceTriggerService.deleteTrigger(symbol, username);
                    break;
                case "price-difference":
                    priceDifferenceTriggerService.deleteTrigger(symbol, username);
                    break;
                case "funding-rate":
                    fundingRateTriggerService.deleteTrigger(symbol, username);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Invalid trigger type");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing trigger: " + e.getMessage());
        }
        return ResponseEntity.ok("Trigger condition deleted successfully.");
    }
}
