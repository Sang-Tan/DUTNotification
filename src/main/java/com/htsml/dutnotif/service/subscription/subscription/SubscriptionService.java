package com.htsml.dutnotif.service.subscription.subscription;

import com.htsml.dutnotif.service.subscription.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.service.subscription.subscription.exception.AlreadySubscribedException;
import com.htsml.dutnotif.service.subscription.subscription.exception.InvalidSubjectException;
import com.htsml.dutnotif.service.subscription.subscription.exception.SubscriberNotExistException;

import java.util.List;

public interface SubscriptionService {
    /**
     * @throws SubscriberNotExistException if subscriber with given id does not exist
     * @throws InvalidSubjectException if subject is not valid
     * @throws AlreadySubscribedException if subscriber is already subscribed to subject
     */
    void subscribe(Integer subscriberId, String subject);

    /**
     * @throws InvalidSubjectException if subject is not valid
     */
    void unsubscribe(Integer subscriberId, String subject);

    List<SubscriberDto> findSubscribersForSubject(String subject, SubscriberTypeEnum subscriberType);
}
