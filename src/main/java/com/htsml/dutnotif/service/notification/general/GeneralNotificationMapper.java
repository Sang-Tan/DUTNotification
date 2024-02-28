package com.htsml.dutnotif.service.notification.general;

import com.htsml.dutnotif.crawl.notification.GeneralNotificationDto;
import com.htsml.dutnotif.repository.notification.general.GeneralNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GeneralNotificationMapper {
    @Mapping(target = "id", ignore = true)
    GeneralNotification toEntity(GeneralNotificationDto generalNotificationDto);

    GeneralNotificationDto toDto(GeneralNotification generalNotification);
}
