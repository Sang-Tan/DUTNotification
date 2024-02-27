package com.htsml.dutnotif.subscribe.subscription;

import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.subscribe.subscription.exception.AlreadySubscribedException;
import com.htsml.dutnotif.subscribe.subscription.exception.InvalidSubjectException;

import java.util.List;

public interface SubscriberTypeAwareSubscriptionService {
    /**
     * @throws InvalidSubjectException if subject is not valid
     * @throws AlreadySubscribedException if subscriber is already subscribed to subject
     */
    void subscribe(String subscriberCode, String subject);

    /**
     * @throws InvalidSubjectException if subject is not valid
     */
    void unsubscribe(String subscriberCode, String subject);

    List<SubscriberDto> findSubscribersForSubject(String subject);
}
