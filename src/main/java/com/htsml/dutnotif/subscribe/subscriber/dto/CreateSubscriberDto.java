package com.htsml.dutnotif.subscribe.subscriber.dto;

import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubscriberDto {
    private String code;

    private SubscriberTypeEnum type;

    private String additionalInfo;
}
