package com.htsml.dutnotif.messenger.send.notif;


import com.htsml.dutnotif.messenger.send.notif.dto.SubscribeNotifDto;
import com.htsml.dutnotif.subscribe.subscriber.dto.SubscriberDto;

public interface MessengerNotifService {

    /**
     * @throws AlreadySubscribedException if the recipient is already subscribed
     */
    void requestOneTimeNotif(String recipientId);

    void subscribeOneTimeNotif(SubscribeNotifDto subscribeNotifDto);

    void sendOneTimeNotif(SubscriberDto subscriber, String text);
}
