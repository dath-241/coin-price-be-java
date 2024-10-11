package com.javaweb.controller.vip0;

import com.javaweb.connect.impl.FundingIntervalWebService;
import com.javaweb.connect.impl.FundingRateWebSocketService;
import com.javaweb.connect.impl.FutureWebSocketService;
import com.javaweb.connect.impl.SpotWebSocketService;
import com.javaweb.dto.FundingIntervalDTO;
import com.javaweb.dto.FundingRateDTO;
import com.javaweb.dto.PriceDTO;
import com.javaweb.config.WebSocketConfig;
import com.javaweb.helpers.sse.SseHelper;
import com.javaweb.helpers.controller.UpperCaseHelper;
import com.javaweb.service.impl.FundingRateDataService;
import com.javaweb.service.impl.FuturePriceDataService;
import com.javaweb.service.impl.SpotPriceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("/vip0")
public class PriceController {
    private final ConcurrentHashMap<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @Autowired
    private SseHelper sseHelper;
    @Autowired
    private WebSocketConfig webSocketConfig;

    @Autowired
    private SpotPriceDataService spotPriceDataService;
    @Autowired
    private FuturePriceDataService futurePriceDataService;
    @Autowired
    private FundingRateDataService fundingRateDataService;

    @Autowired
    private SpotWebSocketService spotWebSocketService;
    @Autowired
    private FutureWebSocketService futureWebSocketService;
    @Autowired
    private FundingRateWebSocketService fundingRateWebSocketService;
    @Autowired
    private FundingIntervalWebService fundingIntervalWebService;

    @GetMapping("/get-spot-price")
    public SseEmitter streamSpotPrices(@RequestParam List<String> symbols) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        spotWebSocketService.connectToWebSocket(symbols);

        Map<String, PriceDTO> priceDataMap = spotPriceDataService.getPriceDataMap();
        return sseHelper.createPriceSseEmitter(emitter, "spot", priceDataMap, webSocketConfig);
    }

    @GetMapping("/get-future-price")
    public SseEmitter streamFuturePrices(@RequestParam List<String> symbols) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        futureWebSocketService.connectToWebSocket(symbols);

        Map<String, PriceDTO> priceDataMap = futurePriceDataService.getPriceDataMap();
        return sseHelper.createPriceSseEmitter(emitter, "future", priceDataMap, webSocketConfig);
    }

    @GetMapping("/get-funding-rate")
    public SseEmitter streamFundingRate(@RequestParam List<String> symbols) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        fundingRateWebSocketService.connectToWebSocket(symbols);

        Map<String, FundingRateDTO> fundingRateDataMap = fundingRateDataService.getFundingRateDataMap();
        return sseHelper.createFundingRateSseEmitter(emitter, "funding-rate", fundingRateDataMap, webSocketConfig);
    }

    @GetMapping("/get-funding-interval")
    public ResponseEntity<List<Map<String, FundingIntervalDTO>>> getFundingInterval(@RequestParam List<String> symbols) {
        List<String> upperCasesymbols = UpperCaseHelper.converttoUpperCase(symbols);

        List<Map<String, FundingIntervalDTO>> fundingIntervalData = fundingIntervalWebService.handleFundingIntervalWeb(upperCasesymbols);
        return ResponseEntity.ok(fundingIntervalData);
    }

    @DeleteMapping("/close-websocket")
    public void closeWebSocket(@RequestParam String type) {
        sseHelper.closeWebSocket(type, webSocketConfig);
    }

    @DeleteMapping("/close-all-websocket")
    public void closeAllWebSocket() {
        sseHelper.closeAllWebSockets(webSocketConfig);
    }

}
