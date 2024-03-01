package com.htsml.dutnotif.service.notification.group;

import com.htsml.dutnotif.back.notification.NotificationAnnouncer;
import com.htsml.dutnotif.crawl.notification.GroupNotificationDto;
import com.htsml.dutnotif.repository.notification.group.GroupNotification;
import com.htsml.dutnotif.repository.notification.group.GroupNotificationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GroupNotificationServiceImpl implements GroupNotificationService {
    private final NotificationAnnouncer notificationAnnouncer;

    private final GroupNotificationMapper groupNotificationMapper;

    private final GroupNotificationRepository groupNotificationRepository;

    public GroupNotificationServiceImpl(NotificationAnnouncer notificationAnnouncer,
                                        GroupNotificationMapper groupNotificationMapper,
                                        GroupNotificationRepository groupNotificationRepository) {
        this.notificationAnnouncer = notificationAnnouncer;
        this.groupNotificationMapper = groupNotificationMapper;
        this.groupNotificationRepository = groupNotificationRepository;
    }


    @Override
    @Transactional
    public void addNotifications(List<GroupNotificationDto> groupNotifications) {
        List<GroupNotificationDto> newNotificationDtos = groupNotifications.stream().filter(
                groupNotificationDto -> !groupNotificationRepository.existsByHash(
                        getMd5Hash(groupNotificationDto)
                )).toList();

        List<GroupNotification> newNotifications = newNotificationDtos.stream().map(
                groupNotification -> {
                    GroupNotification notification = groupNotificationMapper.toEntity(groupNotification);
                    notification.setHash(getMd5Hash(groupNotification));
                    return notification;
                }
        ).toList();

        groupNotificationRepository.saveAll(newNotifications);
        notificationAnnouncer.announceGroupNotifications(newNotificationDtos);
    }

    private byte[] getMd5Hash(GroupNotificationDto notificationDto) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            List<Byte> bytes = new ArrayList<>();
            for (byte b : getBytes(notificationDto.getDate())) {
                bytes.add(b);
            }
            for (byte b : notificationDto.getTitle().getBytes()) {
                bytes.add(b);
            }
            for (byte b : notificationDto.getContent().getBytes()) {
                bytes.add(b);
            }
            for (byte b : notificationDto.getGroup().getBytes()) {
                bytes.add(b);
            }

            byte[] bytesArray = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++) {
                bytesArray[i] = bytes.get(i);
            }

            return messageDigest.digest(bytesArray);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getBytes(Date date) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.putLong(date.getTime());
        return byteBuffer.array();
    }

}
