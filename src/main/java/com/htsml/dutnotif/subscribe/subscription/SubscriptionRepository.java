package com.htsml.dutnotif.subscribe.subscription;

import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.subscribe.subscription.entity.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {

    @Transactional
    void deleteByPrimaryKey(SubscriptionId primaryKey);

    @Transactional
    void deleteByPrimaryKey_Subscriber_IdAndPrimaryKeySubjectIn(Integer subscriberId, List<String> subjects);

    List<Subscription> findAllByPrimaryKey_Subject(String subject);

    List<Subscription> findAllByPrimaryKey_SubjectAndPrimaryKey_Subscriber_Type(String subject, SubscriberTypeEnum type);

    List<Subscription> findAllByPrimaryKey_SubjectIn(List<String> subjects);

    List<Subscription> findAllByPrimaryKey_SubjectInAndPrimaryKey_Subscriber_Type(List<String> subjects, SubscriberTypeEnum type);

    boolean existsByPrimaryKey_SubjectAndPrimaryKey_Subscriber_Id(String subject, Integer subscriberId);
}
