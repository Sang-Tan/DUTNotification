package com.htsml.dutnotif.subscribe.subscriber.type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberTypeEnumTest {

    @Test
    void testMappingFromString() {
        String type = "messenger";
        assertEquals(SubscriberTypeEnum.MESSENGER, SubscriberTypeEnum.of(type));
    }
}