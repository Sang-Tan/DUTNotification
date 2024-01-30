package com.htsml.dutnotif.subscribe.subscription;

import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.subscribe.subscription.entity.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
    void deleteByPrimaryKey(SubscriptionId primaryKey);

    void deleteByPrimaryKey_Subscriber_IdAndPrimaryKeySubjectIn(Integer subscriberId, List<String> subjects);

    List<Subscription> findAllByPrimaryKey_Subject(String subject);

    List<Subscription> findAllByPrimaryKey_SubjectInAndPrimaryKey_Subscriber_Type(List<String> subjects, SubscriberTypeEnum type);

    boolean existsByPrimaryKey_SubjectAndPrimaryKey_Subscriber_Id(String subject, Integer subscriberId);
}
