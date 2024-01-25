package com.htsml.dutnotif.messenger.send.notif;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OneTimeNotifDto {
    private String recipientId;
    private String title;
    private String payload;
}
