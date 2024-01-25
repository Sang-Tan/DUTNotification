package com.htsml.dutnotif.messenger.hook;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/webhooks")
public class WebhookController {
    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
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
        if (mode.equals("subscribe") && verifyToken.equals("dutnotif")) {
            log.debug("Received webhook challenge: {}", challenge);
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.badRequest().build();
    }
}
