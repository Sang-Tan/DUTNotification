package com.htsml.dutnotif.controller.messenger.hook;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/webhooks")
public class WebhookController {
    private final WebhookService webhookService;

    private final String verifyToken;

    public WebhookController(WebhookService webhookService,
                             @Value("${messenger.hook.verify-token}") String verifyToken) {
        this.webhookService = webhookService;
        this.verifyToken = verifyToken;
    }

    @PostMapping
    public void webhook(@RequestBody String body) {
        try {
            log.debug("Received webhook: {}", body);
            webhookService.handleWebhook(body);
        } catch (Exception e) {
            log.error("Error while processing webhook", e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> webhooks(@RequestParam("hub.challenge") String challenge,
                                   @RequestParam("hub.verify_token") String verifyToken,
                                   @RequestParam("hub.mode") String mode) {
        if ("subscribe".equals(mode) && this.verifyToken.equals(verifyToken)) {
            log.debug("Received webhook challenge: {}", challenge);
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.badRequest().build();
    }
}
