package com.htsml.dutnotif.discord.interaction.handler;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.discord.interaction.handler.dto.InteractionResponseDto;

public interface DiscordInteractionHandler {
    InteractionResponseDto handleApplicationCommand(String interaction);

    InteractionResponseDto handleApplicationCommand(ObjectNode interaction);
}
