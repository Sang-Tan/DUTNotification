package com.htsml.dutnotif.messenger.send.chat;

public interface MessengerChatService {
    void sendMessage(String subject, String message);

    void sendEventMessage(String subject, String message);
}
