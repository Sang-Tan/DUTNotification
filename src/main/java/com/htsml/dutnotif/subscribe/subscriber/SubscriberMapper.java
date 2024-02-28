package com.htsml.dutnotif.subscribe.subscriber;

import com.htsml.dutnotif.repository.subscriber.Subscriber;
import com.htsml.dutnotif.subscribe.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {
    SubscriberDto toDto(Subscriber subscriber);

    @Mapping(target = "id", ignore = true)
    Subscriber toEntity(CreateSubscriberDto createSubscriberDto);
}
