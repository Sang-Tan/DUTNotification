package com.htsml.dutnotif.subscribe.subscription;

import com.htsml.dutnotif.subscribe.subscriber.Subscriber;
import com.htsml.dutnotif.subscribe.subscriber.SubscriberMapper;
import com.htsml.dutnotif.subscribe.subscriber.SubscriberService;
import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.subscribe.subscription.dto.CreateSubscriptionDto;
import com.htsml.dutnotif.subscribe.subscription.dto.SearchSubscribersCodeDto;
import com.htsml.dutnotif.subscribe.subscription.entity.SubscriptionId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.htsml.dutnotif.subscribe.subscription.SubjectNames.*;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriberService subscriberService;

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    private final SubscriberMapper subscriberMapper;

    public SubscriptionServiceImpl(SubscriberService subscriberService,
                                   SubscriptionRepository subscriptionRepository,
                                   SubscriptionMapper subscriptionMapper,
                                   SubscriberMapper subscriberMapper) {
        this.subscriberService = subscriberService;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.subscriberMapper = subscriberMapper;
    }

    @Override
    public void subscribe(CreateSubscriptionDto createSubscriptionDto) {
        String subject = createSubscriptionDto.getSubject();

        if (subject == null || subject.isEmpty()) {
            throw new InvalidSubjectException();
        }

        Matcher matcher = majorGroupNamePattern.matcher(subject);
        if (!matcher.matches() &&
                !subject.equals(ALL_GROUP) &&
                !subject.equals(GENERAL)) {
            throw new InvalidSubjectException();
        }

        subscriptionRepository.save(subscriptionMapper.toEntity(createSubscriptionDto));
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
}
