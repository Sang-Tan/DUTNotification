package com.htsml.dutnotif.notification.back;

import com.htsml.dutnotif.messenger.MessengerSubscriptionService;
import com.htsml.dutnotif.messenger.send.chat.MessengerChatService;
import com.htsml.dutnotif.notification.crawl.GeneralNotificationDto;
import com.htsml.dutnotif.notification.general.NewGeneralNotificationEvent;
import com.htsml.dutnotif.subscribe.subscription.SubjectNames;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MessengerNewGeneralNotifInformer implements ApplicationListener<NewGeneralNotificationEvent> {

    private final MessengerChatService messengerChatService;

    private final MessengerSubscriptionService subscriptionService;

    public MessengerNewGeneralNotifInformer(MessengerChatService messengerChatService,
                                            MessengerSubscriptionService subscriptionService) {
        this.messengerChatService = messengerChatService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void onApplicationEvent(NewGeneralNotificationEvent event) {
        GeneralNotificationDto notificationDto = event.getNotificationDto();

        String content = notificationDto.getTitle() + "\n" + notificationDto.getContent();

        subscriptionService.findSubscribersForSubject(SubjectNames.GENERAL).forEach(subscriber ->
                messengerChatService.sendMessage(subscriber.getCode(), content));
    }
}
