package com.htsml.dutnotif.subscribe.subscriber;

import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    Subscriber getByTypeAndCode(SubscriberTypeEnum type, String code);

    boolean existsByTypeAndCode(SubscriberTypeEnum type, String code);
}
