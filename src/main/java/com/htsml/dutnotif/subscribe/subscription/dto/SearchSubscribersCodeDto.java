package com.htsml.dutnotif.subscribe.subscription.dto;

import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
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
