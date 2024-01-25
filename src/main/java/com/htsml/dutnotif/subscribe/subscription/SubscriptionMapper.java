package com.htsml.dutnotif.subscribe.subscription;

import com.htsml.dutnotif.subscribe.subscriber.Subscriber;
import com.htsml.dutnotif.subscribe.subscription.dto.CreateSubscriptionDto;
import com.htsml.dutnotif.subscribe.subscription.dto.SubscriptionDto;
import com.htsml.dutnotif.subscribe.subscription.entity.SubscriptionId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionDto toDto(Subscription subscription);


    default Subscription toEntity(CreateSubscriptionDto createSubscriptionDto) {
        Subscription subscription = new Subscription();


        Subscriber subscriber = new Subscriber();
        subscriber.setId(createSubscriptionDto.getSubscriberId());

        SubscriptionId subscriptionId = new SubscriptionId();
        subscriptionId.setSubscriber(subscriber);
        subscriptionId.setSubject(createSubscriptionDto.getSubject());
        subscription.setPrimaryKey(subscriptionId);

        return subscription;
    }
}
