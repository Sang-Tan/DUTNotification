package com.htsml.dutnotif.subscribe.subscription.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionDto {
    private String subscriberId;

    private String subject;

    private String additionalInfo;
}
