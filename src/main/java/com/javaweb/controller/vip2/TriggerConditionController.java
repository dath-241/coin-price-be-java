package com.javaweb.controller.vip2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.dto.FundingIntervalDTO;
import com.javaweb.dto.trigger.FundingRateTriggerDTO;
import com.javaweb.dto.trigger.FuturePriceTriggerDTO;
import com.javaweb.dto.trigger.ListingDTO;
import com.javaweb.dto.trigger.PriceDifferenceTriggerDTO;
import com.javaweb.dto.trigger.SpotPriceTriggerDTO;
import com.javaweb.service.trigger.CRUD.FundingRateTriggerService;
import com.javaweb.service.trigger.CRUD.FuturePriceTriggerService;
import com.javaweb.service.trigger.CRUD.PriceDifferenceTriggerService;
import com.javaweb.service.trigger.CRUD.SpotPriceTriggerService;
import com.javaweb.service.trigger.GetTriggerService;
import com.javaweb.service.trigger.*;
import com.javaweb.connect.impl.ListingWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private FundingRateTriggerService fundingRateTriggerService; // Assuming this service exists

    @Autowired
    private FundingRateIntervalService fundingRateIntervalService;

    @Autowired
    private ListingWebSocketService listingWebSocketService;

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
                    //fundingRateTriggerService.createTrigger(fundingDTO); // Ensure this method exists in the FundingRateTriggerService
                    break;
                case "new-symbol-listing":
                    ListingDTO listingDTO = objectMapper.convertValue(dtoMap, ListingDTO.class);
                    listingWebSocketService.startWebSocketClient();  // Start WebSocket for new listing trigger
                    break;
                case "funding-rate-interval-changed":
                    FundingIntervalDTO fundingIntervalDTO = objectMapper.convertValue(dtoMap, FundingIntervalDTO.class);
                    fundingRateIntervalService.createFundingIntervalTrigger(fundingIntervalDTO); // Call the correct method
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
                case "funding-rate-interval":
                    fundingRateIntervalService.deleteFundingRateInterval(symbol);
                    break;
                case "new-symbol-listing":
                    listingWebSocketService.stopTokenCheck();
                    break;
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing trigger: " + e.getMessage());
        }
        return ResponseEntity.ok("Trigger condition delete successfully."); // Ensure return here
    }
}
