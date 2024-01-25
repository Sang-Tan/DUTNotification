package com.htsml.dutnotif.messenger.receive;

import com.htsml.dutnotif.messenger.send.chat.MessengerChatService;
import com.htsml.dutnotif.messenger.send.notif.AlreadySubscribedException;
import com.htsml.dutnotif.messenger.send.notif.MessengerNotifService;
import org.springframework.stereotype.Service;

@Service
public class ProcessReceiveChatServiceImpl implements ProcessReceiveChatService {
    private static final String ONE_TIME_NOTIF_CMD = "register";

    private final MessengerChatService chatService;

    private final MessengerNotifService notifService;

    public ProcessReceiveChatServiceImpl(MessengerChatService messengerChatService,
                                         MessengerNotifService notifService) {
        this.chatService = messengerChatService;
        this.notifService = notifService;
    }

    @Override
    public void processMessage(String senderId, String message) {
        if (ONE_TIME_NOTIF_CMD.equals(message)) {
            try {
                notifService.requestOneTimeNotif(senderId);
            } catch (AlreadySubscribedException e) {
                chatService.sendMessage(senderId, "You have already subscribed");
            }
        } else {
            chatService.sendMessage(senderId, "Sorry, I don't understand");
        }
    }
}
