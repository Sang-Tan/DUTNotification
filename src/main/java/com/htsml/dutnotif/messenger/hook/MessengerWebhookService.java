package com.htsml.dutnotif.messenger.hook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.messenger.receive.ProcessReceiveChatService;
import com.htsml.dutnotif.messenger.send.chat.MessengerChatService;
import com.htsml.dutnotif.messenger.send.notif.MessengerNotifService;
import com.htsml.dutnotif.messenger.send.notif.dto.SubscribeNotifDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MessengerWebhookService implements WebhookService {
    private final ObjectMapper objectMapper;

    private final MessengerChatService sendMessageService;

    private final TaskExecutor taskExecutor;

    private final ProcessReceiveChatService processReceiveChatService;

    private final MessengerNotifService notifService;

    public MessengerWebhookService(ObjectMapper objectMapper,
                                   MessengerChatService messengerChatService,
                                   @Qualifier("applicationTaskExecutor") AsyncTaskExecutor taskExecutor,
                                   ProcessReceiveChatService processReceiveChatService,
                                   MessengerNotifService notifService) {
        this.objectMapper = objectMapper;
        this.sendMessageService = messengerChatService;
        this.taskExecutor = taskExecutor;
        this.processReceiveChatService = processReceiveChatService;
        this.notifService = notifService;
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
        JsonNode optin = messaging.get("optin");
        if (message != null) {
            handleMessage((ObjectNode) message, senderId);
        }

        if (optin != null) {
            handleOptin((ObjectNode) optin, senderId);
        }
    }

    private void handleMessage(ObjectNode message, String senderId) {
        String text = message.get("text").asText();
        processReceiveChatService.processMessage(senderId, text);
    }

    private void handleOptin(ObjectNode optin, String senderId) {
        String type = optin.get("type").asText();
        if ("one_time_notif_req".equals(type)) {
            String token = optin.get("one_time_notif_token").asText();
            String payload = optin.get("payload").asText();
            notifService.subscribeOneTimeNotif(SubscribeNotifDto.builder()
                    .subscriberId(senderId)
                    .payload(payload)
                    .token(token)
                    .build());
        }
    }
}
