package com.htsml.dutnotif.crawl;

import com.htsml.dutnotif.notification.crawl.DUTNotificationCrawlerImpl;
import com.htsml.dutnotif.notification.crawl.GroupNotificationDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DUTNotificationCrawlerImplTest {

    private final DUTNotificationCrawlerImpl crawler = new DUTNotificationCrawlerImpl();

    @Test
    void test_getGroupNotifications() {
        List<GroupNotificationDto> notifications = crawler.getGroupNotifications(1);
        assertNotNull(notifications);

        notifications.forEach(notification -> {
            assertNotNull(notification.getDate());
            assertNotNull(notification.getTitle());
            assertNotNull(notification.getContent());
            assertNotNull(notification.getGroup());

            System.out.println("Date: " + notification.getDate());
            System.out.println("Title: " + notification.getTitle());
            System.out.println("Content: " + notification.getContent());
            System.out.println("Group: " + notification.getGroup());
            System.out.println();
        });
    }
}