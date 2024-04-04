package org.capstone.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

@Component
public class KeyGeneratorFinal {
    @Getter
    private static SecretKey secretKey;

    // Method to generate the secret key
    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        if (secretKey == null) {
            // Create a KeyGenerator instance for the HMAC SHA-256 algorithm
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");

            // Generate a random secret key
            secretKey = keyGenerator.generateKey();
        }
        return secretKey;
    }

}