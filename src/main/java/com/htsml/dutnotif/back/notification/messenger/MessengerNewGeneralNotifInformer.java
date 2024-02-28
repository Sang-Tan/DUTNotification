package com.htsml.dutnotif.back.notification.messenger;

import com.htsml.dutnotif.service.messenger.MessengerSubscriptionService;
import com.htsml.dutnotif.api.messenger.MessengerChatSender;
import com.htsml.dutnotif.crawl.notification.GeneralNotificationDto;
import com.htsml.dutnotif.service.notification.general.NewGeneralNotificationEvent;
import com.htsml.dutnotif.service.subscription.subscription.SubjectNames;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MessengerNewGeneralNotifInformer implements ApplicationListener<NewGeneralNotificationEvent> {

    private final MessengerChatSender messengerChatSender;

    private final MessengerSubscriptionService subscriptionService;

    public MessengerNewGeneralNotifInformer(MessengerChatSender messengerChatSender,
                                            MessengerSubscriptionService subscriptionService) {
        this.messengerChatSender = messengerChatSender;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void onApplicationEvent(NewGeneralNotificationEvent event) {
        GeneralNotificationDto notificationDto = event.getNotificationDto();

        String content = notificationDto.getTitle() + "\n" + notificationDto.getContent();

        subscriptionService.findSubscribersForSubject(SubjectNames.GENERAL).forEach(subscriber ->
                messengerChatSender.sendMessage(subscriber.getCode(), content));
    }
}
