package com.htsml.dutnotif.notification.crawl;

import java.util.List;

public interface DUTNotificationCrawler {
    List<GroupNotificationDto> getGroupNotifications(int page);

    List<GeneralNotificationDto> getGeneralNotifications(int page);
}
