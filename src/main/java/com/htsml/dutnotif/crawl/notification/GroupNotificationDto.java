package com.htsml.dutnotif.crawl.notification;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupNotificationDto extends DUTNotificationDto{
    private String group;
}
