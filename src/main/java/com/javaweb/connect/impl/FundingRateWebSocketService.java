package com.javaweb.connect.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.config.WebSocketConfig;
import com.javaweb.connect.IConnectToWebSocketService;
import com.javaweb.service.impl.FundingRateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FundingRateWebSocketService extends TextWebSocketHandler implements IConnectToWebSocketService {

    @Autowired
    private FundingRateDataService fundingRateDataService;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebSocketConfig webSocketConfig;

    @Autowired
    private WebSocketClient webSocketClient;

    private String buildFundingRateWebSocketUrl(List<String> streams) {
        String streamParam = streams.stream().map(s -> s.toLowerCase() + "@markPrice@1s").collect(Collectors.joining("/"));
        return "wss://fstream.binance.com/stream?streams=" + streamParam;
    }
    public void connectToWebSocket(List<String> streams, boolean isTriggerRequest) {
        String wsUrl = buildFundingRateWebSocketUrl(streams);
        // Truyền cờ isTriggerRequest trực tiếp vào handler
        webSocketConfig.connectToWebSocket(wsUrl, webSocketClient, new FungdingRateWebSocketHandler(isTriggerRequest));
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
