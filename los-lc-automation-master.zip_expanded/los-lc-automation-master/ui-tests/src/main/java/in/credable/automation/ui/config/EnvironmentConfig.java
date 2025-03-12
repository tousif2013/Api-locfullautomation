package in.credable.automation.ui.config;

import in.credable.automation.commons.crypto.SecretKeyDecryptor;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties", "system:env", "classpath:ui-config/environments/${env}.properties"})
public interface EnvironmentConfig extends Config {

    @Key("admin.app.base.url")
    String getAdminAppBaseUrl();

    @Key("admin.app.username")
    String getAdminAppUsername();

    @Key("admin.app.password")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getAdminAppPassword();

    @Key("admin.app.otp")
    String getAdminAppOtp();

    @Key("loan.application.config.page.url")
    String getLoanApplicationConfigPageUrl();

    @Key("dealer.auto.approved.program.id")
    Long getDealerAutoApprovedProgramId();

    @Key("dealer.name")
    String getDealerName();

    @Key("dealer.page.no")
    Integer getDealerPageNo();

    @Key("vendor.auto.approved.program.id")
    Long getVendorAutoApprovedProgramId();

    @Key("vendor.name")
    String getVendorName();

    @Key("vendor.page.no")
    Integer getVendorPageNo();
}
