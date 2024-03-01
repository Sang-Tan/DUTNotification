package com.htsml.dutnotif.back.notification;

import com.htsml.dutnotif.api.messenger.MessengerChatSender;
import com.htsml.dutnotif.crawl.notification.GeneralNotificationDto;
import com.htsml.dutnotif.crawl.notification.GroupNotificationDto;
import com.htsml.dutnotif.service.messenger.MessengerSubscriptionService;
import com.htsml.dutnotif.service.subscription.subscription.SubjectNames;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessengerNotificationAnnouncer implements NotificationAnnouncer{
    private final MessengerChatSender messengerChatSender;

    private final MessengerSubscriptionService subscriptionService;

    public MessengerNotificationAnnouncer(MessengerChatSender messengerChatSender,
                                          MessengerSubscriptionService subscriptionService) {
        this.messengerChatSender = messengerChatSender;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void announceGroupNotifications(List<GroupNotificationDto> groupNotifications) {
        groupNotifications.forEach(notificationDto -> {
            String content = notificationDto.getTitle() + "\n" + notificationDto.getContent();

            subscriptionService.findSubscribersForSubject(notificationDto.getGroup())
                    .forEach(subscriber -> messengerChatSender.sendEventMessage(subscriber.getCode(), content));
        });
    }

    @Override
    public void announceGeneralNotifications(List<GeneralNotificationDto> generalNotifications) {
        generalNotifications.forEach(notificationDto -> {
            String content = notificationDto.getTitle() + "\n" + notificationDto.getContent();

            subscriptionService.findSubscribersForSubject(SubjectNames.GENERAL).forEach(subscriber ->
                    messengerChatSender.sendMessage(subscriber.getCode(), content));
        });
    }
}
