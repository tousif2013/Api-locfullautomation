package in.credable.automation.config;

import in.credable.automation.commons.crypto.SecretKeyDecryptor;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"classpath:api-config/integration-data.properties"})
public interface IntegrationDataConfig extends Config {
    @Key("company.cin")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getCompanyCIN();

    @Key("company.pan")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getCompanyPAN();

    @Key("llp.llpin")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getLLPLLPIN();

    @Key("llp.pan")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getLLPPAN();

    @Key("pan.to.gstin.pan.no")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getPanToGstinPanNo();

    @Key("wiremock.gstin.no")
    @EncryptedValue(value = SecretKeyDecryptor.class)
    String getGstinNo();
}
