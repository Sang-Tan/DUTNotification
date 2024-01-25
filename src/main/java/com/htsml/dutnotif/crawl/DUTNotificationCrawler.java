package com.htsml.dutnotif.crawl;

import java.util.List;

public interface DUTNotificationCrawler {
    List<GroupNotificationDto> getGroupNotifications(int page);
}
