package com.htsml.dutnotif.controller.discord.interaction.handler.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.controller.discord.interaction.handler.dto.InteractionResponseDto;
import com.htsml.dutnotif.controller.discord.command.constant.DiscordCommandOptions;
import com.htsml.dutnotif.service.discord.DiscordSubscriptionService;
import com.htsml.dutnotif.service.subscription.subscription.exception.AlreadySubscribedException;
import com.htsml.dutnotif.service.subscription.subscription.exception.InvalidSubjectException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscordSubscriptionCommandHandlerImpl implements DiscordSubscriptionCommandHandler {
    private final DiscordSubscriptionService subscriptionService;

    public DiscordSubscriptionCommandHandlerImpl(DiscordSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public InteractionResponseDto handleSubscribeCommand(String channelId, ObjectNode dataNode) {
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

    @Override
    public InteractionResponseDto handleUnsubscribeCommand(String channelId, ObjectNode dataNode) {
        String subject = getSubject(dataNode);
        InteractionResponseDto response = new InteractionResponseDto();
        try {
            subscriptionService.unsubscribe(channelId, subject);
            response.setContent("Unsubscribed from " + subject);
        } catch (InvalidSubjectException e) {
            response.setContent("Invalid subject: " + subject);
        }

        return response;
    }

    @Override
    public InteractionResponseDto handleListCommand(String channelId) {
        InteractionResponseDto response = new InteractionResponseDto();
        List<String> subjects = subscriptionService.getSubscribedSubjects(channelId);
        if (subjects.isEmpty()) {
            response.setContent("You are not subscribed to any subjects");
        } else {
            String subjectList = String.join(", ", subscriptionService.getSubscribedSubjects(channelId));
            response.setContent("Subscribed subjects: " + subjectList);
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
