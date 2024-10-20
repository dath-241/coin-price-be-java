package com.javaweb.service.trigger;

import com.javaweb.model.trigger.FundingRateInterval;
import com.javaweb.repository.FundingRateIntervalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FundingRateIntervalService {

    @Autowired
    private FundingRateIntervalRepository fundingRateIntervalRepository;

    public FundingRateInterval fetchLatestFundingRateInterval(String symbol) {
        String apiUrl = "https://fapi.binance.com/fapi/v1/fundingRate?symbol=" + symbol + "&limit=1";
        RestTemplate restTemplate = new RestTemplate();
        FundingRateInterval[] fundingRates = restTemplate.getForObject(apiUrl, FundingRateInterval[].class);
    
        if (fundingRates != null && fundingRates.length > 0) {
            FundingRateInterval newInterval = new FundingRateInterval();
            newInterval.setSymbol(fundingRates[0].getSymbol());
            newInterval.setFundingTime(fundingRates[0].getFundingTime());
            newInterval.setNextFundingTime(fundingRates[0].getNextFundingTime());
            return newInterval;
        }
        return null;
    }

    public boolean hasFundingRateIntervalChanged(FundingRateInterval newInterval) {
        FundingRateInterval lastInterval = fundingRateIntervalRepository.findTopBySymbolOrderByTimestampDesc(newInterval.getSymbol());
        return lastInterval == null || lastInterval.getIntervalTime() != newInterval.getIntervalTime(); // Sửa đổi ở đây
    }

    public void saveFundingRateInterval(FundingRateInterval interval) {
        fundingRateIntervalRepository.save(interval);
    }
}
