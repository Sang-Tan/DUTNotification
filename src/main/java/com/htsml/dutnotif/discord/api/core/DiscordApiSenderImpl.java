package com.htsml.dutnotif.discord.api.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class DiscordApiSenderImpl implements DiscordApiSender{
    private static final String apiUrl = "https://discord.com/api/v10";

    private final String appToken;

    public DiscordApiSenderImpl(@Value("${discord.app.token}") String token) {
        this.appToken = token;
    }

    @Override
    public void send(String endpoint, String body, String method) {
        try {
            URL url = new URL(apiUrl + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Authorization", "Bot " + appToken);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error while sending to discord api, response code: " + responseCode + ", body:\n" + connection.getResponseMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
