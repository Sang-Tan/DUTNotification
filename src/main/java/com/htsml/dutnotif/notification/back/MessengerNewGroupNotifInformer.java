package com.htsml.dutnotif.notification.back;

import com.htsml.dutnotif.messenger.MessengerSubscriptionService;
import com.htsml.dutnotif.api.messenger.MessengerChatSender;
import com.htsml.dutnotif.notification.crawl.GroupNotificationDto;
import com.htsml.dutnotif.notification.group.NewGroupNotificationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MessengerNewGroupNotifInformer implements ApplicationListener<NewGroupNotificationEvent> {
    private final MessengerChatSender messengerChatSender;

    private final MessengerSubscriptionService subscriptionService;

    public MessengerNewGroupNotifInformer(MessengerChatSender messengerChatSender,
                                          MessengerSubscriptionService subscriptionService) {
        this.messengerChatSender = messengerChatSender;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void onApplicationEvent(NewGroupNotificationEvent event) {
        GroupNotificationDto notificationDto = event.getGroupNotificationDto();

        String content = notificationDto.getTitle() + "\n" + notificationDto.getContent();

        subscriptionService.findSubscribersForSubject(notificationDto.getGroup())
                .forEach(subscriber -> messengerChatSender.sendEventMessage(subscriber.getCode(), content));
    }
}
