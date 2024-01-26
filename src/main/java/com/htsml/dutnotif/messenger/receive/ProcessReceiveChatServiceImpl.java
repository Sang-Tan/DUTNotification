package com.htsml.dutnotif.messenger.receive;

import com.htsml.dutnotif.messenger.MessengerSubscriptionService;
import com.htsml.dutnotif.messenger.send.chat.MessengerChatService;
import com.htsml.dutnotif.subscribe.subscription.AlreadySubscribedException;
import com.htsml.dutnotif.subscribe.subscription.InvalidSubjectException;
import org.springframework.stereotype.Service;

@Service
public class ProcessReceiveChatServiceImpl implements ProcessReceiveChatService {
    private final MessengerChatService chatService;

    private final MessengerSubscriptionService subscriptionService;

    public ProcessReceiveChatServiceImpl(MessengerChatService messengerChatService,
                                         MessengerSubscriptionService subscriptionService) {
        this.chatService = messengerChatService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void processMessage(String senderId, String message) {
        if (message.startsWith("subscribe")){
            handleSubscribe(senderId, message);
        } else {
            chatService.sendMessage(senderId, "Sorry, I don't understand");

        }
    }

    private void handleSubscribe(String senderId, String message) {
        String[] split = message.split(" ");
        if (split.length != 2) {
            chatService.sendMessage(senderId, "Invalid command");
            return;
        }
        String subject = split[1];
        try {
            subscriptionService.subscribe(senderId, subject);
            chatService.sendMessage(senderId, "You have subscribed to " + subject);
        } catch (InvalidSubjectException e) {
            chatService.sendMessage(senderId, "Invalid subject");
        } catch (AlreadySubscribedException e) {
            chatService.sendMessage(senderId, "You have already subscribed to " + subject);
        }
    }
}
