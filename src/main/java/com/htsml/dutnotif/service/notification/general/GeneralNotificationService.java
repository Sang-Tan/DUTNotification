package com.htsml.dutnotif.service.notification.general;

import com.htsml.dutnotif.notification.crawl.GeneralNotificationDto;

import java.util.List;

public interface GeneralNotificationService {
    /**
     * Add new notifications to database, if notification already exists, ignore it
     * Fire event of new notifications
     */
    void addNotifications(List<GeneralNotificationDto> groupNotifications);
}
