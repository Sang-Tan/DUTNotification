package com.htsml.dutnotif.service.discord;

import com.htsml.dutnotif.repository.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.service.subscription.subscriber.SubscriberService;
import com.htsml.dutnotif.service.subscription.subscription.AbstractSubscriberTypeAwareSubscriptionService;
import com.htsml.dutnotif.service.subscription.subscription.SubscriptionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("discordSubscriptionService")
public class DiscordSubscriptionService extends AbstractSubscriberTypeAwareSubscriptionService {
    public DiscordSubscriptionService(SubscriptionService subscriptionService, SubscriberService subscriberService) {
        super(subscriptionService, subscriberService, SubscriberTypeEnum.DISCORD);
    }
}
