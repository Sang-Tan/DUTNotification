package com.htsml.dutnotif.api.discord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DiscordChannelMessageSenderImpl implements DiscordChannelMessageSender {
    private final DiscordApiSender discordApiSender;

    private final ObjectMapper objectMapper;

    public DiscordChannelMessageSenderImpl(DiscordApiSender discordApiSender,
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
