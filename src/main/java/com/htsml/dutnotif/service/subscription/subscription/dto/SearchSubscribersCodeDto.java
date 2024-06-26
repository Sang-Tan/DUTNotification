package com.htsml.dutnotif.service.subscription.subscription.dto;

import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchSubscribersCodeDto {
    private String groupId;

    private SubscriberTypeEnum subscriberType;
}
