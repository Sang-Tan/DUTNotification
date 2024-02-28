package com.htsml.dutnotif.service.subscription.subscription.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubscriptionDto {
    private String subscriberCode;
    private String subject;
}
