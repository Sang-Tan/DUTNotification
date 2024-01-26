package com.htsml.dutnotif.notification.back;

import com.htsml.dutnotif.messenger.send.notif.MessengerNotifService;
import com.htsml.dutnotif.notification.crawl.GroupNotificationDto;
import com.htsml.dutnotif.notification.group.NewGroupNotificationEvent;
import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.subscribe.subscription.SubscriptionService;
import com.htsml.dutnotif.subscribe.subscription.dto.SearchSubscribersCodeDto;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MessengerNewGroupNotifInformer implements ApplicationListener<NewGroupNotificationEvent> {
    private final MessengerNotifService messengerNotifService;

    private final SubscriptionService subscriptionService;

    public MessengerNewGroupNotifInformer(MessengerNotifService messengerNotifService,
                                          SubscriptionService subscriptionService) {
        this.messengerNotifService = messengerNotifService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void onApplicationEvent(NewGroupNotificationEvent event) {
        GroupNotificationDto notificationDto = event.getGroupNotificationDto();

        String content = notificationDto.getTitle() + "\n" + notificationDto.getContent();

        subscriptionService.findSubscribersForGroup(SearchSubscribersCodeDto.builder()
                .groupId(notificationDto.getGroup())
                .subscriberType(SubscriberTypeEnum.MESSENGER)
                .build()).forEach(subscriber -> messengerNotifService.sendOneTimeNotif(subscriber, content));
    }
}
