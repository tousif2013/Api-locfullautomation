package in.credable.automation.config;

import org.aeonbits.owner.ConfigCache;

public final class ConfigFactory {

    private ConfigFactory() {
    }

    public static EnvironmentConfig getEnvironmentConfig() {
        return ConfigCache.getOrCreate(EnvironmentConfig.class);
    }

    public static IntegrationDataConfig getIntegrationDataConfig() {
        return ConfigCache.getOrCreate(IntegrationDataConfig.class);
    }
}
