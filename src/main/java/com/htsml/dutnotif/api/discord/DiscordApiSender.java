package com.htsml.dutnotif.api.discord;

public interface DiscordApiSender {
    void send(String endpoint, String body, String method);
}
