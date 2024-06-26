package com.htsml.dutnotif.crawl.notification;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DUTNotificationDto {
    private Date date;

    private String title;

    private String content;
}
