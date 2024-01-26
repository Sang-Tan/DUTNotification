package com.htsml.dutnotif.notification.group;

import com.htsml.dutnotif.notification.crawl.GroupNotificationDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewGroupNotificationEvent extends ApplicationEvent {

    private final GroupNotificationDto groupNotificationDto;

    public NewGroupNotificationEvent(Object source,
                                     GroupNotificationDto groupNotificationDto) {
        super(source);
        this.groupNotificationDto = groupNotificationDto;
    }
}
