package com.htsml.dutnotif.controller.messenger.handler;

public interface ProcessReceiveChatService {
    void processMessage(String senderId, String message);
}
