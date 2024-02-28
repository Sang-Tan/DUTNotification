package com.htsml.dutnotif.service.subscription.subscription;

import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.service.subscription.subscriber.SubscriberService;
import com.htsml.dutnotif.service.subscription.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.service.subscription.subscriber.dto.SubscriberDto;

import java.util.List;

public abstract class AbstractSubscriberTypeAwareSubscriptionService implements SubscriberTypeAwareSubscriptionService{
    private final SubscriptionService subscriptionService;

    private final SubscriberService subscriberService;

    private final SubscriberTypeEnum subscriberType;

    public AbstractSubscriberTypeAwareSubscriptionService(SubscriptionService subscriptionService,
                                                          SubscriberService subscriberService,
                                                          SubscriberTypeEnum subscriberType) {
        this.subscriptionService = subscriptionService;
        this.subscriberService = subscriberService;
        this.subscriberType = subscriberType;
    }

    @Override
    public void subscribe(String subscriberCode, String subject) {
        int subscriberId = getOrCreateSubscriber(subscriberCode).getId();
        subscriptionService.subscribe(subscriberId, subject);
    }

    @Override
    public void unsubscribe(String subscriberCode, String subject) {
        int subscriberId = subscriberService.getSubscriberByTypeAndCode(
                subscriberType, subscriberCode).getId();
        subscriptionService.unsubscribe(subscriberId, subject);
    }

    @Override
    public List<SubscriberDto> findSubscribersForSubject(String subject) {
        return subscriptionService.findSubscribersForSubject(subject, subscriberType);
    }

    @Override
    public List<String> getSubscribedSubjects(String subscriberCode) {
        int subscriberId = subscriberService.getSubscriberByTypeAndCode(
                subscriberType, subscriberCode).getId();
        return subscriptionService.getSubscribedSubjects(subscriberId);
    }

    private SubscriberDto getOrCreateSubscriber(String subscriberCode) {
        if (!subscriberService.isSubscriberExist(subscriberType, subscriberCode)) {
            return subscriberService.createSubscriber(CreateSubscriberDto.builder()
                    .type(subscriberType)
                    .code(subscriberCode)
                    .build());
        } else {
            return subscriberService.getSubscriberByTypeAndCode(
                    subscriberType,
                    subscriberCode
            );
        }
    }
}
