package com.htsml.dutnotif.notification.crawl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DUTNotificationMapper {
    @Mapping(target = "group", ignore = true)
    GroupNotificationDto toGroupNotificationDto(DUTNotificationDto dutNotificationDto);

    GeneralNotificationDto toGeneralNotificationDto(DUTNotificationDto dutNotificationDto);
}
