package com.htsml.dutnotif.service.notification.general;

import com.htsml.dutnotif.crawl.notification.GeneralNotificationDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class NewGeneralNotificationEvent extends ApplicationEvent {
    @Getter
    private final GeneralNotificationDto notificationDto;

    public NewGeneralNotificationEvent(Object source, GeneralNotificationDto notificationDto) {
        super(source);
        this.notificationDto = notificationDto;
    }
}
