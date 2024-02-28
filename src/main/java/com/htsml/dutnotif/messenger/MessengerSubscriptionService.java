package com.htsml.dutnotif.messenger;

import com.htsml.dutnotif.subscribe.subscriber.SubscriberService;
import com.htsml.dutnotif.subscribe.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.subscribe.subscription.SubscriberTypeAwareSubscriptionService;
import com.htsml.dutnotif.subscribe.subscription.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messengerSubscriptionService")
public class MessengerSubscriptionService implements SubscriberTypeAwareSubscriptionService {
    private final SubscriptionService subscriptionService;

    private final SubscriberService subscriberService;

    public MessengerSubscriptionService(SubscriptionService subscriptionService,
                                        SubscriberService subscriberService) {
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
                SubscriberTypeEnum.MESSENGER, subscriberCode).getId();
        subscriptionService.unsubscribe(subscriberId, subject);
    }

    @Override
    public List<SubscriberDto> findSubscribersForSubject(String subject) {
        return subscriptionService.findSubscribersForSubject(subject, SubscriberTypeEnum.MESSENGER);
    }

    private SubscriberDto getOrCreateSubscriber(String subscriberCode) {
        if (!subscriberService.isSubscriberExist(SubscriberTypeEnum.MESSENGER, subscriberCode)) {
            return subscriberService.createSubscriber(CreateSubscriberDto.builder()
                    .type(SubscriberTypeEnum.MESSENGER)
                    .code(subscriberCode)
                    .build());
        } else {
            return subscriberService.getSubscriberByTypeAndCode(
                    SubscriberTypeEnum.MESSENGER,
                    subscriberCode
            );
        }
    }
}
