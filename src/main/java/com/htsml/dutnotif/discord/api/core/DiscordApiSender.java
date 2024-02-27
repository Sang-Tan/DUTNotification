package com.htsml.dutnotif.discord.api.core;

public interface DiscordApiSender {
    void send(String endpoint, String body, String method);
}
