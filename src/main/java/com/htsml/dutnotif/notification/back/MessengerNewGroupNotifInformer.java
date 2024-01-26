package com.htsml.dutnotif.notification.back;

import com.htsml.dutnotif.messenger.send.chat.MessengerChatService;
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
    private final MessengerChatService messengerChatService;

    private final SubscriptionService subscriptionService;

    public MessengerNewGroupNotifInformer(MessengerChatService messengerChatService,
                                          SubscriptionService subscriptionService) {
        this.messengerChatService = messengerChatService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void onApplicationEvent(NewGroupNotificationEvent event) {
        GroupNotificationDto notificationDto = event.getGroupNotificationDto();

        String content = notificationDto.getTitle() + "\n" + notificationDto.getContent();

        subscriptionService.findSubscribersForGroup(SearchSubscribersCodeDto.builder()
                .groupId(notificationDto.getGroup())
                .subscriberType(SubscriberTypeEnum.MESSENGER)
                .build()).forEach(subscriber -> messengerChatService.sendEventMessage(subscriber.getCode(), content));
    }
}
