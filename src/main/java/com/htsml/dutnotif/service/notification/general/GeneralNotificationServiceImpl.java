package com.htsml.dutnotif.service.notification.general;

import com.htsml.dutnotif.crawl.notification.GeneralNotificationDto;
import com.htsml.dutnotif.repository.notification.general.GeneralNotification;
import com.htsml.dutnotif.repository.notification.general.GeneralNotificationRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GeneralNotificationServiceImpl implements GeneralNotificationService {
    private final ApplicationEventPublisher eventPublisher;

    private final GeneralNotificationRepository generalNotificationRepository;

    private final GeneralNotificationMapper generalNotificationMapper;

    public GeneralNotificationServiceImpl(ApplicationEventPublisher eventPublisher,
                                          GeneralNotificationRepository generalNotificationRepository,
                                          GeneralNotificationMapper generalNotificationMapper) {
        this.eventPublisher = eventPublisher;
        this.generalNotificationRepository = generalNotificationRepository;
        this.generalNotificationMapper = generalNotificationMapper;
    }

    @Override
    public void addNotifications(List<GeneralNotificationDto> groupNotifications) {
        List<GeneralNotificationDto> newNotificationDtos = groupNotifications.stream().filter(
                groupNotificationDto -> !generalNotificationRepository.existsByHash(
                        getMd5Hash(groupNotificationDto)
                )).toList();

        List<GeneralNotification> newNotifications = newNotificationDtos.stream()
                .map(notificationDto -> {
                    GeneralNotification notification = generalNotificationMapper.toEntity(notificationDto);
                    notification.setHash(getMd5Hash(notificationDto));
                    return notification;
                })
                .toList();

        generalNotificationRepository.saveAll(newNotifications);

        newNotificationDtos.forEach(notification ->
                eventPublisher.publishEvent(new NewGeneralNotificationEvent(this, notification)));
    }

    private byte[] getMd5Hash(GeneralNotificationDto notificationDto) {
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
