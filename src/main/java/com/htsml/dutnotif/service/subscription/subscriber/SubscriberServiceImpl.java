package com.htsml.dutnotif.service.subscription.subscriber;

import com.htsml.dutnotif.repository.subscriber.Subscriber;
import com.htsml.dutnotif.repository.subscriber.SubscriberRepository;
import com.htsml.dutnotif.service.subscription.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.service.subscription.subscriber.dto.SubscriberDto;
import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class SubscriberServiceImpl implements SubscriberService{
    private final SubscriberMapper subscriberMapper;

    private final SubscriberRepository subscriberRepository;

    public SubscriberServiceImpl(SubscriberMapper subscriberMapper, SubscriberRepository subscriberRepository) {
        this.subscriberMapper = subscriberMapper;
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public SubscriberDto createSubscriber(CreateSubscriberDto createSubscriberDto) {
        Subscriber subscriber = subscriberMapper.toEntity(createSubscriberDto);
        return  subscriberMapper.toDto(subscriberRepository.save(subscriber));
    }

    @Override
    public SubscriberDto getSubscriberByTypeAndCode(SubscriberTypeEnum type, String code) {
        Subscriber subscriber = subscriberRepository.getByTypeAndCode(type, code);
        return subscriberMapper.toDto(subscriber);
    }

    @Override
    public boolean isSubscriberExist(SubscriberTypeEnum type, String code) {
        return subscriberRepository.existsByTypeAndCode(type, code);
    }
}
