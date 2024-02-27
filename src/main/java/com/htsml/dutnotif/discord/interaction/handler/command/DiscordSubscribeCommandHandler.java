package com.htsml.dutnotif.discord.interaction.handler.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.discord.interaction.handler.dto.InteractionResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface DiscordSubscribeCommandHandler {
    InteractionResponseDto handleCommand(String channelId, ObjectNode dataNode);
}
