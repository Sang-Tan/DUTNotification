package com.htsml.dutnotif.messenger;

import com.htsml.dutnotif.subscribe.subscriber.Subscriber;
import com.htsml.dutnotif.subscribe.subscriber.SubscriberMapper;
import com.htsml.dutnotif.subscribe.subscriber.SubscriberService;
import com.htsml.dutnotif.subscribe.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.subscribe.subscription.*;
import com.htsml.dutnotif.subscribe.subscription.dto.SearchSubscribersCodeDto;
import com.htsml.dutnotif.subscribe.subscription.entity.SubscriptionId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.htsml.dutnotif.subscribe.subscription.SubjectNames.*;

@Service("messengerSubscriptionService")
public class MessengerSubscriptionService implements SubscriptionService {
    private final SubscriberService subscriberService;

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriberMapper subscriberMapper;

    public MessengerSubscriptionService(SubscriberService subscriberService,
                                        SubscriptionRepository subscriptionRepository,
                                        SubscriberMapper subscriberMapper) {
        this.subscriberService = subscriberService;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriberMapper = subscriberMapper;
    }

    @Override
    public void subscribe(String subscriberCode, String subject) {
        if (subject == null || subject.isEmpty()) {
            throw new InvalidSubjectException();
        }

        Matcher matcher = majorGroupNamePattern.matcher(subject);
        if (!matcher.matches() &&
                !subject.equals(ALL_GROUP) &&
                !subject.equals(GENERAL)) {
            throw new InvalidSubjectException();
        }

        SubscriberDto subscriber = getOrCreateSubscriber(subscriberCode);

        if (subscriptionRepository.existsByPrimaryKey_SubjectAndPrimaryKey_Subscriber_Id(
                subject, subscriber.getId())) {
            throw new AlreadySubscribedException();
        }

        Subscription subscription = new Subscription();
        subscription.setPrimaryKey(SubscriptionId.builder()
                .subscriber(Subscriber.builder()
                        .id(subscriber.getId())
                        .build())
                .subject(subject)
                .build());

        subscriptionRepository.save(subscription);
    }

    @Override
    public void unsubscribe(String subscriberCode, String subject) {
        SubscriberDto subscriber = subscriberService.getSubscriberByTypeAndCode(
                SubscriberTypeEnum.MESSENGER,
                subscriberCode
        );

        subscriptionRepository.deleteByPrimaryKey(
                SubscriptionId.builder()
                .subscriber(Subscriber.builder().id(subscriber.getId()).build())
                .subject(subject)
                .build());
    }

    @Override
    public List<SubscriberDto> findSubscribersForGroup(SearchSubscribersCodeDto searchDto) {
        List<String> subjects = new ArrayList<>();
        subjects.add(ALL_GROUP);

        Matcher matcher = fullGroupNamePattern.matcher(searchDto.getGroupId());
        if (matcher.matches()) {
            Matcher filterMatcher = filterMinorGroupNamePattern.matcher(searchDto.getGroupId());
            if (filterMatcher.find()) {
                subjects.add(filterMatcher.group());
            }
        } else {
            throw new InvalidSubjectException("Invalid group id [" + searchDto.getGroupId() + "]");
        }

        return subscriptionRepository
                .findAllByPrimaryKey_SubjectInAndPrimaryKey_Subscriber_Type(
                        subjects, searchDto.getSubscriberType())
                .stream()
                .map(subscription -> subscriberMapper.toDto(subscription.getPrimaryKey().getSubscriber()))
                .toList();
    }

    private SubscriberDto getOrCreateSubscriber(String subscriberCode) {
        if (!subscriberService.isSubscriberExist(SubscriberTypeEnum.MESSENGER, subscriberCode)) {
            return subscriberService.createSubscriber(CreateSubscriberDto.builder()
                    .type(SubscriberTypeEnum.MESSENGER)
                    .code(subscriberCode)
                    .build());
        } else {
            return subscriberService.getSubscriberByTypeAndCode(
                    SubscriberTypeEnum.MESSENGER,
                    subscriberCode
            );
        }
    }
}
