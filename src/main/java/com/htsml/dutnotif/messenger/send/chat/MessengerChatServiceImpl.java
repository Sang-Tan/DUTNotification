package com.htsml.dutnotif.messenger.send.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.messenger.send.MessengerSendApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MessengerChatServiceImpl implements MessengerChatService {

    private final MessengerSendApi messengerSendApi;

    private final ObjectMapper objectMapper;

    public MessengerChatServiceImpl(MessengerSendApi messengerSendApi,
                                    ObjectMapper objectMapper) {
        this.messengerSendApi = messengerSendApi;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendMessage(String subject, String message) {
        ObjectNode body = objectMapper.createObjectNode();
        body.put("messaging_type", "RESPONSE");
        body.set("recipient", objectMapper.createObjectNode().put("id", subject));
        body.set("message", objectMapper.createObjectNode().put("text", message));

        messengerSendApi.sendMessage(body.toString());
    }

    @Override
    public void sendEventMessage(String subject, String message) {
        ObjectNode body = objectMapper.createObjectNode();
        body.put("messaging_type", "MESSAGE_TAG");
        body.put("tag", "CONFIRMED_EVENT_UPDATE");
        body.set("recipient", objectMapper.createObjectNode()
                .put("id", subject));
        body.set("message", objectMapper.createObjectNode()
                .put("text", message));

        messengerSendApi.sendMessage(body.toString());
    }
}
