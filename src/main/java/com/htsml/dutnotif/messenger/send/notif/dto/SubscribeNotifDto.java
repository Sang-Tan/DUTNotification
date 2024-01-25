package com.htsml.dutnotif.messenger.send.notif.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeNotifDto {
    private String subscriberId;

    private String payload;

    private String token;
}
