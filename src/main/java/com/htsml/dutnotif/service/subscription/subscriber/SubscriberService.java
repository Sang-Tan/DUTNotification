package com.htsml.dutnotif.service.subscription.subscriber;

import com.htsml.dutnotif.service.subscription.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.service.subscription.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;

public interface SubscriberService {
    SubscriberDto createSubscriber(CreateSubscriberDto createSubscriberDto);

    SubscriberDto getSubscriberByTypeAndCode(SubscriberTypeEnum type, String code);

    boolean isSubscriberExist(SubscriberTypeEnum type, String code);
}
