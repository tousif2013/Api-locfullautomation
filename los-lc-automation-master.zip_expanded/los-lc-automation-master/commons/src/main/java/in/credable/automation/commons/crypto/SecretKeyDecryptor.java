package in.credable.automation.commons.crypto;

import in.credable.automation.commons.exception.EncryptionException;
import in.credable.automation.commons.utils.EncryptionUtil;
import org.aeonbits.owner.crypto.AbstractDecryptor;
import org.apache.commons.lang3.StringUtils;

public final class SecretKeyDecryptor extends AbstractDecryptor {
    private static final String SECRET_KEY;

    static {
        String secretKeyPropertyName = "secret.key";
        if (StringUtils.isNotBlank(System.getProperty(secretKeyPropertyName))) {
            SECRET_KEY = StringUtils.trim(System.getProperty(secretKeyPropertyName));
        } else if (StringUtils.isNotBlank(System.getenv(secretKeyPropertyName))) {
            SECRET_KEY = StringUtils.trim(System.getenv(secretKeyPropertyName));
        } else {
            SECRET_KEY = "";
        }
    }

    @Override
    public String decrypt(String value) {
        if (StringUtils.isBlank(SECRET_KEY)) {
            throw new EncryptionException("'secret.key' is not provided as system property or environment variable");
        }
        return EncryptionUtil.decrypt(value, SECRET_KEY);
    }
}
