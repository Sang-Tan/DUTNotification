package com.htsml.dutnotif.service.discord;

import com.htsml.dutnotif.service.subscription.subscriber.SubscriberService;
import com.htsml.dutnotif.service.subscription.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.service.subscription.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.service.subscription.subscription.SubscriberTypeAwareSubscriptionService;
import com.htsml.dutnotif.service.subscription.subscription.SubscriptionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("discordSubscriptionService")
public class DiscordSubscriptionService implements SubscriberTypeAwareSubscriptionService {

    private final SubscriptionService subscriptionService;

    private final SubscriberService subscriberService;

    public DiscordSubscriptionService(SubscriptionService subscriptionService, SubscriberService subscriberService) {
        this.subscriptionService = subscriptionService;
        this.subscriberService = subscriberService;
    }

    @Override
    public void subscribe(String subscriberCode, String subject) {
        int subscriberId = getOrCreateSubscriber(subscriberCode).getId();
        subscriptionService.subscribe(subscriberId, subject);
    }

    @Override
    public void unsubscribe(String subscriberCode, String subject) {
        int subscriberId = subscriberService.getSubscriberByTypeAndCode(
                SubscriberTypeEnum.DISCORD, subscriberCode).getId();
        subscriptionService.unsubscribe(subscriberId, subject);
    }

    @Override
    public List<SubscriberDto> findSubscribersForSubject(String subject) {
        return subscriptionService.findSubscribersForSubject(subject, SubscriberTypeEnum.DISCORD);
    }

    private SubscriberDto getOrCreateSubscriber(String subscriberCode) {
        if (!subscriberService.isSubscriberExist(SubscriberTypeEnum.DISCORD, subscriberCode)) {
            return subscriberService.createSubscriber(CreateSubscriberDto.builder()
                    .type(SubscriberTypeEnum.DISCORD)
                    .code(subscriberCode)
                    .build());
        } else {
            return subscriberService.getSubscriberByTypeAndCode(
                    SubscriberTypeEnum.DISCORD,
                    subscriberCode
            );
        }
    }
}
