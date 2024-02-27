package com.htsml.dutnotif.discord.interaction.security;

import lombok.extern.log4j.Log4j2;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.Utils;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PublicKey;

@Log4j2
@Component
public class DiscordRequestVerifierImpl implements DiscordRequestVerifier{

    private final byte[] PUBLIC_KEY;

    public DiscordRequestVerifierImpl(@Value("${discord.app.public-key}") String publicKey) {
        this.PUBLIC_KEY = Utils.hexToBytes(publicKey);
    }

    @Override
    public boolean verifySignature(String signature, String timestamp, String body) {
        try {
            EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
            EdDSAPublicKeySpec pubKey = new EdDSAPublicKeySpec(PUBLIC_KEY, spec);
            EdDSAEngine sgr = new EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));
            PublicKey vKey = new EdDSAPublicKey(pubKey);
            sgr.initVerify(vKey);

            String message = timestamp + body;
            return sgr.verifyOneShot(message.getBytes(StandardCharsets.UTF_8), Utils.hexToBytes(signature));
        } catch (Exception e) {
            log.error("Failed to verify signature", e);
            return false;
        }
    }
}
