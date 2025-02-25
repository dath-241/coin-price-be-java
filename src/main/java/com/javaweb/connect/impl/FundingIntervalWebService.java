package com.javaweb.connect.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.dto.FundingIntervalDTO;
import com.javaweb.connect.IFundingIntervalWebService;
import com.javaweb.service.impl.FundingIntervalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FundingIntervalWebService implements IFundingIntervalWebService {

    @Autowired
    private FundingIntervalDataService fundingIntervalDataService;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String FUNDINGINTERVAL_API_URL = "https://fapi.binance.com/fapi/v1/fundingInfo?symbol=";

    @Override
    public Map<String, FundingIntervalDTO> getLatestFundingIntervalData(List<String> symbols) {
        return handleFundingIntervalWeb(symbols);
    }

    @Override
    public Map<String, FundingIntervalDTO> handleFundingIntervalWeb(List<String> symbols) {
        Map<String, FundingIntervalDTO> fundingIntervalDataList = new HashMap<>();

        for (String symbol : symbols) {
            Map<String, FundingIntervalDTO> cachedData = fundingIntervalDataService.processFundingIntervalDataFromCache(symbol);

            if (cachedData != null && !cachedData.isEmpty()) {
                fundingIntervalDataList.putAll(cachedData);
            } else {
                String url = FUNDINGINTERVAL_API_URL + symbol;
                JsonNode response = restTemplate.getForObject(url, JsonNode.class);

                if (response != null && response.isArray()) {
                    for (JsonNode fundingInfo : response) {
                        if (fundingInfo.get("symbol").asText().equals(symbol)) {
                            System.out.println("Received data from API for symbol: " + symbol);

                            Map<String, FundingIntervalDTO> processedData = fundingIntervalDataService.processFundingIntervalData(fundingInfo);
                            fundingIntervalDataList.putAll(processedData);
                        }
                    }
                } else {
                    System.out.println("No valid data received for symbol: " + symbol);
                }
            }
        }

        return fundingIntervalDataList;
    }
}
