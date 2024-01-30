package com.htsml.dutnotif.notification.crawl;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupNotificationDto extends DUTNotificationDto{
    private String group;
}
