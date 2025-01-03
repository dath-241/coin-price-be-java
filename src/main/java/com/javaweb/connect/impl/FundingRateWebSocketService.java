package com.javaweb.connect.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.config.WebSocketConfig;
import com.javaweb.connect.IConnectToWebSocketService;
import com.javaweb.service.impl.FundingRateDataService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FundingRateWebSocketService extends TextWebSocketHandler implements IConnectToWebSocketService {
    private final Set<String> subscribedSymbols = ConcurrentHashMap.newKeySet();
    private FundingRateDataService fundingRateDataService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketConfig webSocketConfig;
    private WebSocketClient webSocketClient;

    private String buildFundingRateWebSocketUrl(Set<String> streams) {
        String streamParam = streams.stream().map(s -> s.toLowerCase() + "@markPrice@1s").collect(Collectors.joining("/"));
        return "wss://fstream.binance.com/stream?streams=" + streamParam;
    }
    public synchronized void connectToWebSocket(List<String> streams, boolean isTriggerRequest) {

        boolean hasNewSymbols = subscribedSymbols.addAll(streams);

        if (hasNewSymbols || webSocketConfig.isSessionClosed()) {
            String wsUrl = buildFundingRateWebSocketUrl(subscribedSymbols);
            // Truyền cờ isTriggerRequest trực tiếp vào handler
            webSocketConfig.connectToWebSocket(wsUrl, webSocketClient, new FungdingRateWebSocketHandler(isTriggerRequest));
        }

    }

    private class FungdingRateWebSocketHandler extends TextWebSocketHandler {
        private final boolean isTriggerRequest;

        public FungdingRateWebSocketHandler(boolean isTriggerRequest) {
            this.isTriggerRequest = isTriggerRequest;
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            String payload = message.getPayload();
            JsonNode data = objectMapper.readTree(payload).get("data");

            fundingRateDataService.handleFundingRateWebSocketMessage(data, isTriggerRequest);
        }
    }
}