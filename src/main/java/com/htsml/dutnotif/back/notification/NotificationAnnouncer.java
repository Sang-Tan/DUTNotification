package com.htsml.dutnotif.back.notification;

import com.htsml.dutnotif.crawl.notification.GeneralNotificationDto;
import com.htsml.dutnotif.crawl.notification.GroupNotificationDto;

import java.util.List;

public interface NotificationAnnouncer {
    void announceGroupNotifications(List<GroupNotificationDto> groupNotifications);
    void announceGeneralNotifications(List<GeneralNotificationDto> generalNotifications);
}
