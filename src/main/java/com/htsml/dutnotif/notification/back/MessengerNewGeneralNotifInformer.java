package com.htsml.dutnotif.notification.back;

import com.htsml.dutnotif.messenger.send.chat.MessengerChatService;
import com.htsml.dutnotif.notification.crawl.GeneralNotificationDto;
import com.htsml.dutnotif.notification.general.NewGeneralNotificationEvent;
import com.htsml.dutnotif.subscribe.subscription.SubscriptionService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MessengerNewGeneralNotifInformer implements ApplicationListener<NewGeneralNotificationEvent> {

    private final MessengerChatService messengerChatService;

    private final SubscriptionService subscriptionService;

    public MessengerNewGeneralNotifInformer(MessengerChatService messengerChatService,
                                            SubscriptionService subscriptionService) {
        this.messengerChatService = messengerChatService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void onApplicationEvent(NewGeneralNotificationEvent event) {
        GeneralNotificationDto notificationDto = event.getNotificationDto();

        String content = notificationDto.getTitle() + "\n" + notificationDto.getContent();

        subscriptionService.getGeneralSubscribers().forEach(subscriber ->
                messengerChatService.sendMessage(subscriber.getCode(), content));
    }
}
