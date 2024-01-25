package com.htsml.dutnotif.subscribe.subscription;

import com.htsml.dutnotif.subscribe.subscription.entity.SubscriptionId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @EmbeddedId
    private SubscriptionId primaryKey;
}
