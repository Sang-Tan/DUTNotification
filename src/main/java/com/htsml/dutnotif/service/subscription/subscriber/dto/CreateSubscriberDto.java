package com.htsml.dutnotif.service.subscription.subscriber.dto;

import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubscriberDto {
    private String code;

    private SubscriberTypeEnum type;

    private String additionalInfo;
}
