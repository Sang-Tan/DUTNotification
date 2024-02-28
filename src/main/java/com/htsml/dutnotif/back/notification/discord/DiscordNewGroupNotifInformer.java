package com.htsml.dutnotif.back.notification.discord;

import com.htsml.dutnotif.api.discord.DiscordChannelMessageSender;
import com.htsml.dutnotif.crawl.notification.GroupNotificationDto;
import com.htsml.dutnotif.service.discord.DiscordSubscriptionService;
import com.htsml.dutnotif.service.notification.group.NewGroupNotificationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DiscordNewGroupNotifInformer implements ApplicationListener<NewGroupNotificationEvent> {
    private final DiscordSubscriptionService subscriptionService;

    private final DiscordChannelMessageSender messageSender;

    public DiscordNewGroupNotifInformer(DiscordSubscriptionService subscriptionService,
                                        DiscordChannelMessageSender messageSender) {
        this.subscriptionService = subscriptionService;
        this.messageSender = messageSender;
    }

    @Override
    public void onApplicationEvent(NewGroupNotificationEvent event) {
        GroupNotificationDto groupNotificationDto = event.getGroupNotificationDto();
        String content = groupNotificationDto.getTitle() + "\n" + groupNotificationDto.getContent();

        subscriptionService
                .findSubscribersForSubject(groupNotificationDto.getGroup())
                .forEach(subscriber -> messageSender.sendMessage(subscriber.getCode(), content));
    }
}
