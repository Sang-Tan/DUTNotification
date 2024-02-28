package com.htsml.dutnotif.subscribe.subscriber.dto;

import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriberDto {
    private Integer id;

    private String code;

    private SubscriberTypeEnum type;

    private String additionalInfo;
}
