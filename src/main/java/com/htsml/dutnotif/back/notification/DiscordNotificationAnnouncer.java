package com.htsml.dutnotif.back.notification;

import com.htsml.dutnotif.api.discord.DiscordChannelMessageSender;
import com.htsml.dutnotif.crawl.notification.GeneralNotificationDto;
import com.htsml.dutnotif.crawl.notification.GroupNotificationDto;
import com.htsml.dutnotif.service.discord.DiscordSubscriptionService;
import com.htsml.dutnotif.service.subscription.subscription.SubjectNames;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscordNotificationAnnouncer implements NotificationAnnouncer{
    private final DiscordSubscriptionService subscriptionService;

    private final DiscordChannelMessageSender messageSender;

    public DiscordNotificationAnnouncer(DiscordSubscriptionService subscriptionService, DiscordChannelMessageSender messageSender) {
        this.subscriptionService = subscriptionService;
        this.messageSender = messageSender;
    }

    @Override
    public void announceGroupNotifications(List<GroupNotificationDto> groupNotifications) {
        //TODO: optimize this
        groupNotifications.forEach(groupNotificationDto -> {
            String content = groupNotificationDto.getTitle() + "\n" + groupNotificationDto.getContent();

            subscriptionService
                    .findSubscribersForSubject(groupNotificationDto.getGroup())
                    .forEach(subscriber -> messageSender.sendMessage(subscriber.getCode(), content));
        });
    }

    @Override
    public void announceGeneralNotifications(List<GeneralNotificationDto> generalNotifications) {
        //TODO: optimize this
        generalNotifications.forEach(generalNotificationDto -> {
            String content = generalNotificationDto.getTitle() + "\n" + generalNotificationDto.getContent();

            subscriptionService
                    .findSubscribersForSubject(SubjectNames.GENERAL)
                    .forEach(subscriber -> messageSender.sendMessage(subscriber.getCode(), content));
        });
    }
}
