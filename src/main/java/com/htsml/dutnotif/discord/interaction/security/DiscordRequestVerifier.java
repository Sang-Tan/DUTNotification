package com.htsml.dutnotif.discord.interaction.security;

public interface DiscordRequestVerifier {
    boolean verifySignature(String signature, String timestamp, String body);
}
