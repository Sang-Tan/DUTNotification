package com.htsml.dutnotif.api.messenger;

public interface MessengerChatSender {
    void sendMessage(String subject, String message);

    void sendEventMessage(String subject, String message);
}
