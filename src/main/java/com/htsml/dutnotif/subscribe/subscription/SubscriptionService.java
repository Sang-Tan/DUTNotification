package com.htsml.dutnotif.subscribe.subscription;

import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.subscribe.subscription.dto.CreateSubscriptionDto;
import com.htsml.dutnotif.subscribe.subscription.dto.SearchSubscribersCodeDto;

import java.util.List;

public interface SubscriptionService {
    /**
     * @throws InvalidSubjectException if subject is not valid
     */
    void subscribe(CreateSubscriptionDto createSubscriptionDto);

    void unsubscribe(String subscriberId, String subject);

    List<SubscriberDto> findSubscribersForGroup(SearchSubscribersCodeDto searchDto);
}
