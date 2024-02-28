package com.htsml.dutnotif.controller.discord.interaction.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.controller.discord.interaction.handler.command.DiscordSubscribeCommandHandler;
import com.htsml.dutnotif.controller.discord.interaction.handler.dto.InteractionResponseDto;
import com.htsml.dutnotif.controller.discord.command.constant.DiscordCommandNames;
import com.htsml.dutnotif.controller.discord.command.constant.DiscordCommandTypes;
import org.springframework.stereotype.Component;

@Component
public class DiscordInteractionHandlerImpl implements DiscordInteractionHandler {
    private final ObjectMapper objectMapper;

    private final DiscordSubscribeCommandHandler discordSubscribeCommandHandler;


    public DiscordInteractionHandlerImpl(ObjectMapper objectMapper,
                                         DiscordSubscribeCommandHandler discordSubscribeCommandHandler) {
        this.objectMapper = objectMapper;
        this.discordSubscribeCommandHandler = discordSubscribeCommandHandler;
    }

    @Override
    public InteractionResponseDto handleApplicationCommand(String interaction) {
        ObjectNode interactionNode = getInteractionNode(interaction);
        return handleApplicationCommand(interactionNode);
    }

    @Override
    public InteractionResponseDto handleApplicationCommand(ObjectNode interaction) {
        ObjectNode dataNode = (ObjectNode) interaction.get("data");

        int commandType = dataNode.get("type").asInt();

        if (commandType != DiscordCommandTypes.CHAT_INPUT) {
            throw new RuntimeException("Command type not supported: " + commandType);
        }

        String commandName = dataNode.get("name").asText();

        if (DiscordCommandNames.SUBSCRIBE.equals(commandName)) {
            String channelId = interaction.get("channel_id").asText();
            return discordSubscribeCommandHandler.handleCommand(channelId, dataNode);
        }

        throw new RuntimeException("Command not supported: " + commandName);
    }

    private ObjectNode getInteractionNode(String interaction) {
        try {
            return objectMapper.readValue(interaction, ObjectNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
