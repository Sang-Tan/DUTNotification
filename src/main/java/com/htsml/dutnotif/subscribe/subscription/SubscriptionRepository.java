package com.htsml.dutnotif.subscribe.subscription;

import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.subscribe.subscription.entity.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
    void deleteByPrimaryKey(SubscriptionId primaryKey);

    List<Subscription> findAllByPrimaryKey_Subject(String subject);

    List<Subscription> findAllByPrimaryKey_SubjectInAndPrimaryKey_Subscriber_Type(List<String> subjects, SubscriberTypeEnum type);
}
