package in.credable.automation.config;

import in.credable.automation.commons.crypto.SecretKeyDecryptor;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties", "system:env", "classpath:api-config/environments/${env}.properties"})
public interface EnvironmentConfig extends Config {
    @Key("base.url")
    String getBaseUrl();

    @Key("notification.mode.sms")
    int getNotificationModeForSMS();

    @Key("notification.mode.email")
    int getNotificationModeForEmail();

    @Key("keycloak.organization.id")
    String getKeyCloakOrganizationId();

    @Key("keycloak.client.id")
    String getKeyCloakClientId();

    @Key("keycloak.client.secret")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getKeyCloakClientSecret();

    @Key("client.id.keycloak.organization")
    int getClientIdForKeycloakOrganization();


    @Key("dealer.program.id")
    Long getDealerProgramId();

    @Key("vendor.program.id")
    Long getVendorProgramId();

    @Key("admin.app.user.name")
    String getAdminAppUserName();

    @Key("integration.source.id")
    String getIntegrationSourceId();

    @Key("integration.client.id")
    String getIntegrationClientId();

    @Key("otp")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getOTP();

    @Key("admin.username.2fa")
    String getAdminUserName2FA();

    @Key("admin.password.2fa")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getAdminPassword2FA();

    @Key("admin.username")
    String getAdminUserName();

    @Key("admin.password")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getAdminPassword();

    @Key("borrower.2fa.mobileno")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getBorrower2FAMobileNo();

    @Key("admin.username.resetPassword")
    String getAdminUserNameForResetpassword();

    @Key("sso.encryption.key.hex")
    String getSsoEncryptionKeyHex();

    @Key("admin.username.multipleSendOtp")
    String getAdminUserNameForMultipleSendOtp();

    @Key("automation.client.id")
    Long getAutomationClientId();

    @Key("automation.anchor.id")
    Long getAutomationAnchorId();

    @Key("automation.program.id")
    Long getAutomationProgramId();
}
