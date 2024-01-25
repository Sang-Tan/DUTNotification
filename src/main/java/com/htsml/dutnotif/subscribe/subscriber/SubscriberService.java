package com.htsml.dutnotif.subscribe.subscriber;

import com.htsml.dutnotif.subscribe.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;

public interface SubscriberService {
    void createSubscriber(CreateSubscriberDto createSubscriberDto);

    SubscriberDto getSubscriberByTypeAndCode(SubscriberTypeEnum type, String code);

    boolean isSubscriberExist(SubscriberTypeEnum type, String code);
}
