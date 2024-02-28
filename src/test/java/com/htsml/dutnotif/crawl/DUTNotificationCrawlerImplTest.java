package com.htsml.dutnotif.crawl;

import com.htsml.dutnotif.crawl.notification.DUTNotificationCrawlerImpl;
import com.htsml.dutnotif.crawl.notification.GeneralNotificationDto;
import com.htsml.dutnotif.crawl.notification.GroupNotificationDto;
import com.htsml.dutnotif.notification.crawl.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {DUTNotificationCrawlerImpl.class, DUTNotificationMapperImpl.class})
class DUTNotificationCrawlerImplTest {

    @Autowired
    DUTNotificationCrawlerImpl crawler;

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

    @Test
    void test_getGeneralNotifications(){
        List<GeneralNotificationDto> notifications = crawler.getGeneralNotifications(1);
        assertNotNull(notifications);

        notifications.forEach(notification -> {
            assertNotNull(notification.getDate());
            assertNotNull(notification.getTitle());
            assertNotNull(notification.getContent());

            System.out.println("Date: " + notification.getDate());
            System.out.println("Title: " + notification.getTitle());
            System.out.println("Content: " + notification.getContent());
            System.out.println();
        });
    }
}