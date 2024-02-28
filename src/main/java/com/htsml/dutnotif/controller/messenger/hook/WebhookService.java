package com.htsml.dutnotif.controller.messenger.hook;

public interface WebhookService {
    void handleWebhook(String body);
}
