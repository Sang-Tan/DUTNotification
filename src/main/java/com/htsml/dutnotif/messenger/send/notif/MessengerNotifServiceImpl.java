package com.htsml.dutnotif.messenger.send.notif;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.htsml.dutnotif.messenger.send.MessengerSendApi;
import com.htsml.dutnotif.messenger.send.notif.dto.SubscribeNotifDto;
import com.htsml.dutnotif.subscribe.subscriber.SubscriberService;
import com.htsml.dutnotif.subscribe.subscriber.dto.CreateSubscriberDto;
import com.htsml.dutnotif.subscribe.subscriber.type.SubscriberTypeEnum;
import com.htsml.dutnotif.subscribe.subscription.SubscriptionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MessengerNotifServiceImpl implements MessengerNotifService {
    private static final String PAYLOAD = "SUBSCRIBE_ONE_TIME_NOTIF";

    private final ObjectMapper objectMapper;

    private final MessengerSendApi messengerSendApi;

    private final SubscriptionService subscriptionService;

    private final SubscriberService subscriberService;

    public MessengerNotifServiceImpl(ObjectMapper objectMapper,
                                     MessengerSendApi messengerSendApi,
                                     SubscriptionService subscriptionService,
                                     SubscriberService subscriberService) {
        this.objectMapper = objectMapper;
        this.messengerSendApi = messengerSendApi;
        this.subscriptionService = subscriptionService;
        this.subscriberService = subscriberService;
    }

    @Override
    public void requestOneTimeNotif(String recipientId) {
        if (subscriberService.isSubscriberExist(SubscriberTypeEnum.MESSENGER, recipientId)) {
            throw new AlreadySubscribedException();
        }

        ObjectNode body = objectMapper.createObjectNode();
        body.set("recipient", objectMapper.createObjectNode().put("id", recipientId));
        body.set("message", objectMapper.createObjectNode()
                .set("attachment", objectMapper.createObjectNode()
                        .put("type", "template")
                        .set("payload", objectMapper.createObjectNode()
                                .put("template_type", "one_time_notif_req")
                                .put("title", "Request Notification")
                                .put("payload", PAYLOAD))));

        messengerSendApi.sendMessage(body.toString());
    }

    @Override
    public void subscribeOneTimeNotif(SubscribeNotifDto subscribeNotifDto) {
        if (!PAYLOAD.equals(subscribeNotifDto.getPayload())) {
            throw new IllegalArgumentException("Invalid payload");
        }

        if (subscriberService.isSubscriberExist(SubscriberTypeEnum.MESSENGER, subscribeNotifDto.getSubscriberId())) {
            log.warn("Subscriber [{}, {}] tried to subscribe twice",
                    SubscriberTypeEnum.MESSENGER, subscribeNotifDto.getSubscriberId());
            return;
        }

        CreateSubscriberDto subscriberDto = new CreateSubscriberDto();
        subscriberDto.setType(SubscriberTypeEnum.MESSENGER);
        subscriberDto.setCode(subscribeNotifDto.getSubscriberId());
        subscriberDto.setAdditionalInfo(subscribeNotifDto.getToken());

        subscriberService.createSubscriber(subscriberDto);
    }

    @Override
    public void sendOneTimeNotif(String subscriberId) {
        ObjectNode body = objectMapper.createObjectNode();
        body.set("recipient", objectMapper.createObjectNode()
                .put("id", subscriberId));
        body.set("message", objectMapper.createObjectNode()
                .set("attachment", objectMapper.createObjectNode()
                        .put("type", "template")
                        .set("payload", objectMapper.createObjectNode()
                                .put("template_type", "generic")
                                .set("elements", objectMapper.createArrayNode()
                                        .add(objectMapper.createObjectNode()
                                                .put("title", "One Time Notification")
                                                .put("subtitle", "This is a one time notification")
                                                .set("buttons", objectMapper.createArrayNode()
                                                        .add(objectMapper.createObjectNode()
                                                                .put("type", "postback")
                                                                .put("title", "Click Here")
                                                                .put("payload", PAYLOAD))))))));

        messengerSendApi.sendMessage(body.toString());
    }
}
