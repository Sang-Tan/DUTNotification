package com.htsml.dutnotif.notification.back;

import com.htsml.dutnotif.notification.crawl.DUTNotificationCrawler;
import com.htsml.dutnotif.notification.crawl.GroupNotificationDto;
import com.htsml.dutnotif.notification.group.GroupNotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
public class NotificationUpdateWorker {
    private final DUTNotificationCrawler dutNotificationCrawler;

    private final GroupNotificationService groupNotificationService;

    public NotificationUpdateWorker(DUTNotificationCrawler dutNotificationCrawler,
                                    GroupNotificationService groupNotificationService) {
        this.dutNotificationCrawler = dutNotificationCrawler;
        this.groupNotificationService = groupNotificationService;
    }

    @Scheduled(fixedRateString = "${notification.group.update.interval-min}",timeUnit = TimeUnit.MINUTES)
    public void updateGroupNotifications() {
        log.info("Updating group notifications...");
        try {
            List<GroupNotificationDto> notificationDtos =
                    dutNotificationCrawler.getGroupNotifications(1);
            groupNotificationService.addNotifications(notificationDtos);
        } catch (Exception e) {
            log.error("Error while updating group notifications", e);
        }

    }
}
