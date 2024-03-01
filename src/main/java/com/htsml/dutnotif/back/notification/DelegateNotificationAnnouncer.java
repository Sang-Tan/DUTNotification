package com.htsml.dutnotif.back.notification;

import com.htsml.dutnotif.crawl.notification.GeneralNotificationDto;
import com.htsml.dutnotif.crawl.notification.GroupNotificationDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Primary
@Component
public class DelegateNotificationAnnouncer implements NotificationAnnouncer {
    private final DiscordNotificationAnnouncer discordNotificationAnnouncer;

    private final MessengerNotificationAnnouncer messengerNotificationAnnouncer;

    public DelegateNotificationAnnouncer(DiscordNotificationAnnouncer discordNotificationAnnouncer, MessengerNotificationAnnouncer messengerNotificationAnnouncer) {
        this.discordNotificationAnnouncer = discordNotificationAnnouncer;
        this.messengerNotificationAnnouncer = messengerNotificationAnnouncer;
    }

    @Override
    public void announceGroupNotifications(List<GroupNotificationDto> groupNotifications) {
        discordNotificationAnnouncer.announceGroupNotifications(groupNotifications);
        messengerNotificationAnnouncer.announceGroupNotifications(groupNotifications);
    }

    @Override
    public void announceGeneralNotifications(List<GeneralNotificationDto> generalNotifications) {
        discordNotificationAnnouncer.announceGeneralNotifications(generalNotifications);
        messengerNotificationAnnouncer.announceGeneralNotifications(generalNotifications);
    }
}
