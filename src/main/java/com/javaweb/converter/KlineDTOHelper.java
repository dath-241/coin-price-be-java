package com.javaweb.converter;

import com.javaweb.dto.KlineDTO;

public class KlineDTOHelper {
    public static KlineDTO createKlineDTO(String symbol, String klineStartTime, String klineCloseTime, String openPrice, String closePrice, String highPrice, String lowPrice, Long numberOfTrades, String baseAssetVolume, String takerBuyVolume, String takerBuyBaseVolume, String volume, String eventTime) {
        return new KlineDTO.Builder()
                .symbol(symbol)
                .eventTime(eventTime)
                .klineStartTime(klineStartTime)
                .klineCloseTime(klineCloseTime)
                .openPrice(openPrice)
                .closePrice(closePrice)
                .highPrice(highPrice)
                .lowPrice(lowPrice)
                .numberOfTrades(numberOfTrades)
                .baseAssetVolume(baseAssetVolume)
                .takerBuyVolume(takerBuyVolume)
                .takerBuyBaseVolume(takerBuyBaseVolume)
                .volume(volume)
                .build();
    }
}
