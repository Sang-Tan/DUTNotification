package com.htsml.dutnotif.service.messenger;

import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.service.subscription.subscriber.SubscriberService;
import com.htsml.dutnotif.service.subscription.subscription.AbstractSubscriberTypeAwareSubscriptionService;
import com.htsml.dutnotif.service.subscription.subscription.SubscriptionService;
import org.springframework.stereotype.Service;

@Service("messengerSubscriptionService")
public class MessengerSubscriptionService extends AbstractSubscriberTypeAwareSubscriptionService {

    public MessengerSubscriptionService(SubscriptionService subscriptionService,
                                        SubscriberService subscriberService) {
        super(subscriptionService, subscriberService, SubscriberTypeEnum.MESSENGER);
    }
}
