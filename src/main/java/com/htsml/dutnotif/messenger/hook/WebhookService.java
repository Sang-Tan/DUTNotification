package com.htsml.dutnotif.messenger.hook;

public interface WebhookService {
    void handleWebhook(String body);
}
