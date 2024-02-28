package com.htsml.dutnotif.service.notification.group;

import com.htsml.dutnotif.notification.crawl.GroupNotificationDto;

import java.util.List;

public interface GroupNotificationService {
    /**
     * Add new notifications to database, if notification already exists, ignore it
     * Fire event of new notifications
     */
    void addNotifications(List<GroupNotificationDto> groupNotifications);
}
