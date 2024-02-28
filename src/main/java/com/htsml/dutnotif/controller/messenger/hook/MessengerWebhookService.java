package com.htsml.dutnotif.controller.messenger.hook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.controller.messenger.handler.ProcessReceiveChatService;
import com.htsml.dutnotif.api.messenger.MessengerChatSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MessengerWebhookService implements WebhookService {
    private final ObjectMapper objectMapper;

    private final MessengerChatSender sendMessageService;

    private final TaskExecutor taskExecutor;

    private final ProcessReceiveChatService processReceiveChatService;

    public MessengerWebhookService(ObjectMapper objectMapper,
                                   MessengerChatSender messengerChatSender,
                                   @Qualifier("applicationTaskExecutor") AsyncTaskExecutor taskExecutor,
                                   ProcessReceiveChatService processReceiveChatService) {
        this.objectMapper = objectMapper;
        this.sendMessageService = messengerChatSender;
        this.taskExecutor = taskExecutor;
        this.processReceiveChatService = processReceiveChatService;
    }

    @Override
    public void handleWebhook(String body) {
        taskExecutor.execute(() -> {
            try {
                doHandleWebhook(body);
            } catch (Exception e) {
                log.error("Error while handling webhook", e);
            }
        });
    }

    private void doHandleWebhook(String body) throws JsonProcessingException {
        ObjectNode webhookPayload = objectMapper.readValue(body, ObjectNode.class);
        ArrayNode entries = (ArrayNode) webhookPayload.get("entry");
        entries.forEach(entry -> handleEntry((ObjectNode) entry));
    }

    private void handleEntry(ObjectNode entry) {
        ArrayNode messaging = (ArrayNode) entry.get("messaging");
        messaging.forEach(messagingNode -> handleMessaging((ObjectNode) messagingNode));
    }

    private void handleMessaging(ObjectNode messaging) {
        ObjectNode sender = (ObjectNode) messaging.get("sender");
        String senderId = sender.get("id").asText();
        JsonNode message = messaging.get("message");
        if (message != null) {
            handleMessage((ObjectNode) message, senderId);
        }
    }

    private void handleMessage(ObjectNode message, String senderId) {
        String text = message.get("text").asText();
        processReceiveChatService.processMessage(senderId, text);
    }
}
