package com.htsml.dutnotif.subscribe.subscriber;

import com.htsml.dutnotif.subscribe.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;

public interface SubscriberService {
    SubscriberDto createSubscriber(CreateSubscriberDto createSubscriberDto);

    SubscriberDto getSubscriberByTypeAndCode(SubscriberTypeEnum type, String code);

    boolean isSubscriberExist(SubscriberTypeEnum type, String code);
}
