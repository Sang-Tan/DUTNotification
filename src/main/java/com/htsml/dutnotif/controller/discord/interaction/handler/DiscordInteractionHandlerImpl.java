package com.htsml.dutnotif.controller.discord.interaction.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.controller.discord.interaction.handler.command.DiscordSubscriptionCommandHandler;
import com.htsml.dutnotif.controller.discord.interaction.handler.dto.InteractionResponseDto;
import com.htsml.dutnotif.controller.discord.command.constant.DiscordCommandNames;
import com.htsml.dutnotif.controller.discord.command.constant.DiscordCommandTypes;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DiscordInteractionHandlerImpl implements DiscordInteractionHandler {
    private final ObjectMapper objectMapper;

    private final DiscordSubscriptionCommandHandler discordSubscriptionCommandHandler;


    public DiscordInteractionHandlerImpl(ObjectMapper objectMapper,
                                         DiscordSubscriptionCommandHandler discordSubscriptionCommandHandler) {
        this.objectMapper = objectMapper;
        this.discordSubscriptionCommandHandler = discordSubscriptionCommandHandler;
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

        return handleCommandByName(dataNode.get("name").asText(), interaction);
    }

    private InteractionResponseDto handleCommandByName(String commandName, ObjectNode interaction) {
        return switch (commandName) {
            case DiscordCommandNames.SUBSCRIBE -> handleSubscribeCommand(interaction);
            case DiscordCommandNames.UNSUBSCRIBE -> handleUnsubscribeCommand(interaction);
            default -> throw new RuntimeException("Command not supported: " + commandName);
        };
    }

    private InteractionResponseDto handleSubscribeCommand(ObjectNode interaction) {
        ObjectNode dataNode = (ObjectNode) interaction.get("data");
        String channelId = interaction.get("channel_id").asText();

        return discordSubscriptionCommandHandler.handleSubscribeCommand(channelId, dataNode);
    }

    private InteractionResponseDto handleUnsubscribeCommand(ObjectNode interaction) {
        ObjectNode dataNode = (ObjectNode) interaction.get("data");
        String channelId = interaction.get("channel_id").asText();

        return discordSubscriptionCommandHandler.handleUnsubscribeCommand(channelId, dataNode);
    }

    private ObjectNode getInteractionNode(String interaction) {
        try {
            return objectMapper.readValue(interaction, ObjectNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
