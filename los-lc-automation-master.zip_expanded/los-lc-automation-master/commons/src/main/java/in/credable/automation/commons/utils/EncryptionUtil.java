package in.credable.automation.commons.utils;


import in.credable.automation.commons.exception.EncryptionException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Log4j2
public final class EncryptionUtil {
    private static final String TRANSFORMATION = "AES";

    public static String encrypt(String text, String key) throws EncryptionException {
        try {
            final Cipher cipher = getCipher(key, Cipher.ENCRYPT_MODE);
            // encrypt the text
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            byte[] encryptedByteValue = Base64.getEncoder().encode(encrypted);
            return new String(encryptedByteValue, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Could not encrypt given string", e);
            throw new EncryptionException("Could not encrypt given string", e);
        }
    }

    public static String decrypt(String encryptedValue, String key) throws EncryptionException {
        try {
            final Cipher cipher = getCipher(key, Cipher.DECRYPT_MODE);
            // decrypt the text
            final byte[] decodeStr = Base64.getDecoder().decode(encryptedValue);
            return new String(cipher.doFinal(decodeStr), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Could not decrypt given string", e);
            throw new EncryptionException("Could not decrypt given string", e);
        }
    }

    private static Cipher getCipher(String key, Integer mode) throws InvalidKeyException,
            NoSuchPaddingException, NoSuchAlgorithmException, DecoderException {
        byte[] keyBytes = Hex.decodeHex(key.toCharArray());
        Key aesKey = new SecretKeySpec(keyBytes, TRANSFORMATION);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(mode, aesKey);
        return cipher;
    }

}
