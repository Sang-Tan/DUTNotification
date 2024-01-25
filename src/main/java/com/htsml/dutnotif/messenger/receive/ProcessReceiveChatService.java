package com.htsml.dutnotif.messenger.receive;

public interface ProcessReceiveChatService {
    void processMessage(String senderId, String message);
}
