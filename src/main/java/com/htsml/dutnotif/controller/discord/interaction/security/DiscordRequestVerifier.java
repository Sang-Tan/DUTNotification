package com.htsml.dutnotif.controller.discord.interaction.security;

public interface DiscordRequestVerifier {
    boolean verifySignature(String signature, String timestamp, String body);
}
