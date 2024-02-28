package com.htsml.dutnotif.controller.discord.interaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.controller.discord.interaction.constant.InteractionCallbackTypes;
import com.htsml.dutnotif.controller.discord.interaction.constant.InteractionTypes;
import com.htsml.dutnotif.controller.discord.interaction.handler.DiscordInteractionHandler;
import com.htsml.dutnotif.controller.discord.interaction.handler.dto.InteractionResponseDto;
import com.htsml.dutnotif.controller.discord.interaction.security.DiscordRequestVerifier;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/discord/interactions")
public class DiscordInteractionsController {
    private final ObjectMapper objectMapper;

    private final DiscordRequestVerifier discordRequestVerifier;

    private final DiscordInteractionHandler discordInteractionHandler;

    public DiscordInteractionsController(ObjectMapper objectMapper,
                                         DiscordRequestVerifier discordRequestVerifier,
                                         DiscordInteractionHandler discordInteractionHandler) {
        this.discordRequestVerifier = discordRequestVerifier;
        this.objectMapper = objectMapper;
        this.discordInteractionHandler = discordInteractionHandler;
    }

    @PostMapping
    public ResponseEntity<Object> handleInteraction(
            @RequestHeader("X-Signature-Ed25519") String signature,
            @RequestHeader("X-Signature-Timestamp") String timestamp,
            @RequestBody String interaction) throws JsonProcessingException {
        log.trace("Received interaction: {}", interaction);
        log.trace("Signature: {}", signature);
        log.trace("Timestamp: {}", timestamp);

        // Verify the request signature
        if (!discordRequestVerifier.verifySignature(signature, timestamp, interaction)) {
            log.warn("Invalid request signature");
            return ResponseEntity.status(401).body("invalid request signature");
        }

        ObjectNode interactionJson = objectMapper.readValue(interaction, ObjectNode.class);
        int type = interactionJson.get("type").asInt();

        try {
            ObjectNode response = handleInteraction(type, interactionJson);
            log.trace("Responding with: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to handle interaction", e);
            ObjectNode response = objectMapper.createObjectNode()
                    .put("type", InteractionCallbackTypes.CHANNEL_MESSAGE_WITH_SOURCE)
                    .set("data", objectMapper.createObjectNode()
                            .put("content", "Oops! Something went wrong :3"));
            return ResponseEntity.ok(response);
        }
    }

    private ObjectNode handleInteraction(int type, ObjectNode interactionJson) {
        return switch (type) {
            case InteractionTypes.PING -> pingInteractionResponse();
            case InteractionTypes.APPLICATION_COMMAND -> applicationCommandInteractionResponse(interactionJson);
            default -> throw new IllegalArgumentException("Unknown interaction type: " + type);
        };
    }

    private ObjectNode pingInteractionResponse() {
        return objectMapper.createObjectNode().put("type", InteractionCallbackTypes.PONG);
    }

    private ObjectNode applicationCommandInteractionResponse(ObjectNode interactionJson) {
        InteractionResponseDto responseDto =
                discordInteractionHandler.handleApplicationCommand(interactionJson);
        return objectMapper.createObjectNode()
                .put("type", InteractionCallbackTypes.CHANNEL_MESSAGE_WITH_SOURCE)
                .set("data",  objectMapper.createObjectNode()
                        .put("content", responseDto.getContent()));
    }
}
