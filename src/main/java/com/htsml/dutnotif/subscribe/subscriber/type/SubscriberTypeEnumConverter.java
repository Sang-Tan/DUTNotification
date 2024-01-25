package com.htsml.dutnotif.subscribe.subscriber.type;

import jakarta.persistence.AttributeConverter;

public class SubscriberTypeEnumConverter implements AttributeConverter<SubscriberTypeEnum, String> {
    @Override
    public String convertToDatabaseColumn(SubscriberTypeEnum attribute) {
        return attribute.getType();
    }

    @Override
    public SubscriberTypeEnum convertToEntityAttribute(String dbData) {
        return SubscriberTypeEnum.of(  dbData);
    }
}
