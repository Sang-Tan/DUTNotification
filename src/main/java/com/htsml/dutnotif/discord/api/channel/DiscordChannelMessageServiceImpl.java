package com.htsml.dutnotif.discord.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.discord.api.core.DiscordApiSender;
import org.springframework.stereotype.Service;

@Service
public class DiscordChannelMessageServiceImpl implements DiscordChannelMessageService {
    private final DiscordApiSender discordApiSender;

    private final ObjectMapper objectMapper;

    public DiscordChannelMessageServiceImpl(DiscordApiSender discordApiSender,
                                            ObjectMapper objectMapper) {
        this.discordApiSender = discordApiSender;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendMessage(String channelId, String message) {
        ObjectNode body = objectMapper.createObjectNode();
        body.put("content", message);

        discordApiSender.send(String.format("/channels/%s/messages", channelId), body.toString(), "POST");
    }
}
