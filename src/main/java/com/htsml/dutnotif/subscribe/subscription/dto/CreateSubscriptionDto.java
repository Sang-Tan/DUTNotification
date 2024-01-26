package com.htsml.dutnotif.subscribe.subscription.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubscriptionDto {
    private String subscriberCode;
    private String subject;
}
