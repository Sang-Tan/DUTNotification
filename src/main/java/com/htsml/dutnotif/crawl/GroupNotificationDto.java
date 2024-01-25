package com.htsml.dutnotif.crawl;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupNotificationDto {
    Date date;

    String title;

    String content;

    String group;
}
