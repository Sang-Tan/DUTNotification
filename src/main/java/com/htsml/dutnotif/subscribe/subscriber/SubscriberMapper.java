package com.htsml.dutnotif.subscribe.subscriber;

import com.htsml.dutnotif.subscribe.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {
    SubscriberDto toDto(Subscriber subscriber);

    Subscriber toEntity(CreateSubscriberDto createSubscriberDto);
}
