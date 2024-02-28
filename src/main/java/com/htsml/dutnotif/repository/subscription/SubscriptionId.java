package com.htsml.dutnotif.repository.subscription;

import com.htsml.dutnotif.repository.subscriber.Subscriber;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionId implements Serializable {
    private String subject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id", referencedColumnName = "id")
    private Subscriber subscriber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionId that = (SubscriptionId) o;
        return Objects.equals(subject, that.subject) && Objects.equals(subscriber.getId(), that.subscriber.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, subscriber.getId());
    }
}
