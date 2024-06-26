package com.htsml.dutnotif.service.subscription.subscription;

import com.htsml.dutnotif.repository.subscriber.Subscriber;
import com.htsml.dutnotif.repository.subscriber.SubscriberRepository;
import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.repository.subscription.Subscription;
import com.htsml.dutnotif.repository.subscription.SubscriptionId;
import com.htsml.dutnotif.repository.subscription.SubscriptionRepository;
import com.htsml.dutnotif.service.subscription.subscriber.SubscriberMapper;
import com.htsml.dutnotif.service.subscription.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.service.subscription.subscription.exception.AlreadySubscribedException;
import com.htsml.dutnotif.service.subscription.subscription.exception.InvalidSubjectException;
import com.htsml.dutnotif.service.subscription.subscription.exception.SubscriberNotExistException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.htsml.dutnotif.service.subscription.subscription.SubjectNames.*;

@Log4j2
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    private final SubscriberRepository subscriberRepository;

    private final SubscriberMapper subscriberMapper;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   SubscriberRepository subscriberRepository,
                                   SubscriberMapper subscriberMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriberRepository = subscriberRepository;
        this.subscriberMapper = subscriberMapper;
    }

    @Override
    public void subscribe(Integer subscriberId, String subject) {
        if (subject == null || subject.isEmpty()) {
            throw new InvalidSubjectException();
        }

        if (!isSubjectForSubscriptionValid(subject)) {
            throw new InvalidSubjectException();
        }

        if (!subscriberRepository.existsById(subscriberId)) {
            throw new SubscriberNotExistException();
        }

        if (subscriptionRepository.existsByPrimaryKey_SubjectAndPrimaryKey_Subscriber_Id(
                subject, subscriberId)) {
            throw new AlreadySubscribedException();
        }

        Subscription subscription = new Subscription();
        subscription.setPrimaryKey(SubscriptionId.builder()
                .subscriber(Subscriber.builder()
                        .id(subscriberId)
                        .build())
                .subject(subject)
                .build());

        subscriptionRepository.save(subscription);
    }

    @Override
    public void unsubscribe(Integer subscriberId, String subject) {
        if (!isSubjectForSubscriptionValid(subject)) {
            throw new InvalidSubjectException();
        }

        SubscriberDto subscriber = subscriberRepository.findById(subscriberId)
                .map(subscriberMapper::toDto)
                .orElseThrow(SubscriberNotExistException::new);

        if (GENERAL.equals(subject)) {
            subscriptionRepository.deleteByPrimaryKey_Subscriber_IdAndPrimaryKeySubjectIn(
                    subscriber.getId(), List.of(GENERAL));
        } else {
            List<String> subjects = new ArrayList<>();
            subjects.add(ALL_GROUP);
            Matcher matcher = majorGroupNamePattern.matcher(subject);
            if (matcher.matches()) {
                subjects.add(subject);
            }

            subscriptionRepository.deleteByPrimaryKey_Subscriber_IdAndPrimaryKeySubjectIn(
                    subscriber.getId(), subjects);
        }
    }

    @Override
    public List<SubscriberDto> findSubscribersForSubject(String subject, SubscriberTypeEnum subscriberType) {
        if (GENERAL.equals(subject)) {
            return getGeneralSubscribers(subscriberType);
        } else {
            return getGroupSubscribers(subject, subscriberType);
        }
    }

    @Override
    public List<String> getSubscribedSubjects(Integer subscriberId) {
        return subscriptionRepository
                .findAllByPrimaryKey_Subscriber_Id(subscriberId)
                .stream()
                .map(Subscription::getPrimaryKey)
                .map(SubscriptionId::getSubject)
                .toList();
    }

    private List<SubscriberDto> getGroupSubscribers(String groupName, SubscriberTypeEnum subscriberType) {
        List<String> subjects = new ArrayList<>();
        subjects.add(ALL_GROUP);

        Matcher matcher = fullGroupNamePattern.matcher(groupName);
        if (matcher.matches()) {
            Matcher filterMatcher = filterMinorGroupNamePattern.matcher(groupName);
            if (filterMatcher.find()) {
                subjects.add(filterMatcher.group());
            }
        } else {
            throw new InvalidSubjectException("Invalid group id [" + groupName + "]");
        }

        return subscriptionRepository
                .findAllByPrimaryKey_SubjectInAndPrimaryKey_Subscriber_Type(
                        subjects, subscriberType)
                .stream()
                .map(subscription -> subscriberMapper.toDto(subscription.getPrimaryKey().getSubscriber()))
                .toList();
    }

    private List<SubscriberDto> getGeneralSubscribers(SubscriberTypeEnum subscriberType) {
        return subscriptionRepository
                .findAllByPrimaryKey_SubjectAndPrimaryKey_Subscriber_Type(GENERAL, subscriberType)
                .stream()
                .map(subscription -> subscriberMapper.toDto(subscription.getPrimaryKey().getSubscriber()))
                .toList();
    }

    private boolean isSubjectForSubscriptionValid(String subject) {
        Matcher matcher = majorGroupNamePattern.matcher(subject);
        return matcher.matches() ||
                subject.equals(ALL_GROUP) ||
                subject.equals(GENERAL);
    }
}
