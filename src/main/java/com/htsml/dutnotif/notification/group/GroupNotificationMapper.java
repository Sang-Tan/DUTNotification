package com.htsml.dutnotif.notification.group;

import com.htsml.dutnotif.notification.crawl.GroupNotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupNotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hash", ignore = true)
    @Mapping(target = "groupId", source = "group")
    GroupNotification toEntity(GroupNotificationDto groupNotificationDto);
}
