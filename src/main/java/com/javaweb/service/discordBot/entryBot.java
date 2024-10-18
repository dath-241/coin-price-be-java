package com.javaweb.service.discordBot;

import lombok.Data;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Data
public class entryBot {
    @Value("${spring.boot.env.DISCORD_WEBHOOK_ENTRY}")
    private String webhook;

    private String username = "Entry Bot";
    private String img_url  = "https://thumb.ac-illust.com/58/58ff85105c6f6471fa8debd7a205dfcc_t.jpeg";

    public void sendMessage(String message) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        String jsonBody = String.format("{\"username\": \"%s\", \"content\": \"```%s```\", \"avatar_url\": \"%s\"}",
                username, message, img_url);

        RequestBody body = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url(webhook)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Unexpected code " + response);
            } else {
                System.out.println("Message sent successfully: " + response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
