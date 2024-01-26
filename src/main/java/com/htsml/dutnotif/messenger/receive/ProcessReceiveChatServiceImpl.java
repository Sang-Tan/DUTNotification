package com.htsml.dutnotif.messenger.receive;

import com.htsml.dutnotif.messenger.send.chat.MessengerChatService;
import org.springframework.stereotype.Service;

@Service
public class ProcessReceiveChatServiceImpl implements ProcessReceiveChatService {
    private final MessengerChatService chatService;

    public ProcessReceiveChatServiceImpl(MessengerChatService messengerChatService) {
        this.chatService = messengerChatService;
    }

    @Override
    public void processMessage(String senderId, String message) {
        chatService.sendMessage(senderId, "Sorry, I don't understand");
    }
}
