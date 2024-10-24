package com.javaweb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.dto.FundingIntervalDTO;

import java.util.Map;

public interface IFundingIntervalDataService {
    Map<String, FundingIntervalDTO> processFundingIntervalData(JsonNode fundingIntervalData);

    // Method to check cache before calling API
    Map<String, FundingIntervalDTO> processFundingIntervalDataFromCache(String symbol);
}
