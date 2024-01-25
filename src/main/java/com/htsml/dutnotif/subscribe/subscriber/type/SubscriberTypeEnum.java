package com.htsml.dutnotif.subscribe.subscriber.type;

public enum SubscriberTypeEnum {
    MESSENGER("messenger");

    private final String type;

    SubscriberTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static SubscriberTypeEnum of(String value) {
        for (SubscriberTypeEnum type : SubscriberTypeEnum.values()) {
            if (type.getType().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No such subscriber type");
    }
}
