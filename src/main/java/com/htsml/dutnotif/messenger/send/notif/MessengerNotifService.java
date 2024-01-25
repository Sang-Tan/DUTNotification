package com.htsml.dutnotif.messenger.send.notif;


import com.htsml.dutnotif.messenger.send.notif.dto.SubscribeNotifDto;

public interface MessengerNotifService {

    /**
     * @throws AlreadySubscribedException if the recipient is already subscribed
     */
    void requestOneTimeNotif(String recipientId);

    void subscribeOneTimeNotif(SubscribeNotifDto subscribeNotifDto);

    void sendOneTimeNotif(String subscriberId);
}
