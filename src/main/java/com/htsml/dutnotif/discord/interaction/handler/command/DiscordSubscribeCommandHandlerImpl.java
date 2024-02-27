package com.htsml.dutnotif.discord.interaction.handler.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.discord.command.constant.DiscordCommandOptions;
import com.htsml.dutnotif.discord.interaction.handler.dto.InteractionResponseDto;
import com.htsml.dutnotif.discord.subscribe.DiscordSubscriptionService;
import com.htsml.dutnotif.subscribe.subscription.exception.AlreadySubscribedException;
import com.htsml.dutnotif.subscribe.subscription.exception.InvalidSubjectException;
import org.springframework.stereotype.Component;

@Component
public class DiscordSubscribeCommandHandlerImpl implements DiscordSubscribeCommandHandler {
    private final DiscordSubscriptionService subscriptionService;

    public DiscordSubscribeCommandHandlerImpl(DiscordSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public InteractionResponseDto handleCommand(String channelId, ObjectNode dataNode) {
        String subject = getSubject(dataNode);
        InteractionResponseDto response = new InteractionResponseDto();
        try {
            subscriptionService.subscribe(channelId, subject);
            response.setContent("Subscribed to " + subject);
        } catch (InvalidSubjectException e) {
            response.setContent("Invalid subject: " + subject);
        } catch (AlreadySubscribedException e) {
            response.setContent("You have already subscribed to " + subject);
        }

        return response;
    }

    private String getSubject(ObjectNode dataNode) {
        ObjectNode optionsNode = (ObjectNode) dataNode.get("options").get(0);
        String subscribeType = optionsNode.get("name").asText();

        if (DiscordCommandOptions.SUBSCRIBE_GENERAL.equals(subscribeType)) {
            return "general";
        } else if (DiscordCommandOptions.SUBSCRIBE_GROUP.equals(subscribeType)) {
            return optionsNode.get("options").get(0).get("value").asText();
        } else {
            throw new IllegalArgumentException("Invalid subscribe type");
        }
    }
}
