package com.htsml.dutnotif.messenger.send;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Log4j2
@Service("v18MessengerSendApi")
public class V18MessengerSendApi implements MessengerSendApi {
    private final URL apiUrl;

    public V18MessengerSendApi(@Value("${messenger.api.page-id}") String pageId,
                               @Value("${messenger.api.page-access-token}") String accessToken) {
        try {
            apiUrl = new URL(String.format("https://graph.facebook.com/v18.0/%s/messages?access_token=%s", pageId, accessToken));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendMessage(String body) {
        try{
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body.getBytes());
            outputStream.flush();
            outputStream.close();

            log.debug("Sending message to messenger:\n Method: {}\n URL: {}\n body: {}", connection.getRequestMethod(), connection.getURL(), body);
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                log.error("Error while sending message, response code: {}", responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
