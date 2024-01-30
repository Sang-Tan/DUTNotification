package com.htsml.dutnotif.notification.general;

import com.htsml.dutnotif.notification.crawl.GeneralNotificationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeneralNotificationMapper {
    GeneralNotification toEntity(GeneralNotificationDto generalNotificationDto);

    GeneralNotificationDto toDto(GeneralNotification generalNotification);
}
