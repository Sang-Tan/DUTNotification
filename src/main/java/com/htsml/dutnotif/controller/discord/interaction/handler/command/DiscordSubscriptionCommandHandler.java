package com.htsml.dutnotif.controller.discord.interaction.handler.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.controller.discord.interaction.handler.dto.InteractionResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface DiscordSubscriptionCommandHandler {
    InteractionResponseDto handleSubscribeCommand(String channelId, ObjectNode dataNode);
    InteractionResponseDto handleUnsubscribeCommand(String channelId, ObjectNode dataNode);
    InteractionResponseDto handleListCommand(String channelId);
}
