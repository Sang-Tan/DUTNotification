package com.htsml.dutnotif.subscribe.subscriber.type;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subscriber_types")
public class SubscriberType {
    @Id
    private String id;
}
