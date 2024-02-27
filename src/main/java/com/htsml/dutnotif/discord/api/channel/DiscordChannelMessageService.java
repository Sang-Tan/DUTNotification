package com.htsml.dutnotif.discord.api.channel;

import org.springframework.stereotype.Service;

public interface DiscordChannelMessageService {
    void sendMessage(String channelId, String message);
}
