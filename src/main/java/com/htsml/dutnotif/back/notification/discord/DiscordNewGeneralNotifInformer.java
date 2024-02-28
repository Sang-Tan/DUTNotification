package com.htsml.dutnotif.back.notification.discord;

import com.htsml.dutnotif.api.discord.DiscordChannelMessageSender;
import com.htsml.dutnotif.service.discord.DiscordSubscriptionService;
import com.htsml.dutnotif.service.notification.general.NewGeneralNotificationEvent;
import com.htsml.dutnotif.service.subscription.subscription.SubjectNames;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DiscordNewGeneralNotifInformer implements ApplicationListener<NewGeneralNotificationEvent> {
    private final DiscordSubscriptionService subscriptionService;

    private final DiscordChannelMessageSender messageSender;

    public DiscordNewGeneralNotifInformer(DiscordSubscriptionService subscriptionService, DiscordChannelMessageSender messageSender) {
        this.subscriptionService = subscriptionService;
        this.messageSender = messageSender;
    }

    @Override
    public void onApplicationEvent(NewGeneralNotificationEvent event) {
        String content = event.getNotificationDto().getTitle() + "\n" + event.getNotificationDto().getContent();

        subscriptionService
                .findSubscribersForSubject(SubjectNames.GENERAL)
                .forEach(subscriber -> messageSender.sendMessage(subscriber.getCode(), content));
    }
}
