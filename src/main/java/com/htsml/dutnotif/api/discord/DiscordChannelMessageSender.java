package com.htsml.dutnotif.api.discord;

public interface DiscordChannelMessageSender {
    void sendMessage(String channelId, String message);
}
