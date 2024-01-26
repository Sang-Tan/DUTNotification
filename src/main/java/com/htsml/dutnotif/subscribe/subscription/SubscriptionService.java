package com.htsml.dutnotif.subscribe.subscription;

import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.subscribe.subscription.dto.CreateSubscriptionDto;
import com.htsml.dutnotif.subscribe.subscription.dto.SearchSubscribersCodeDto;

import java.util.List;

public interface SubscriptionService {
    /**
     * @throws InvalidSubjectException if subject is not valid
     * @throws AlreadySubscribedException if subscriber is already subscribed to subject
     */
    void subscribe(String subscriberCode, String subject);

    void unsubscribe(String subscriberCode, String subject);

    List<SubscriberDto> findSubscribersForGroup(SearchSubscribersCodeDto searchDto);
}
