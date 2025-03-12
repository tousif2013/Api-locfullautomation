package in.credable.automation.ui.config;

import org.aeonbits.owner.ConfigCache;

public final class ConfigFactory {
    private ConfigFactory() {
    }

    public static EnvironmentConfig getEnvironmentConfig() {
        return ConfigCache.getOrCreate(EnvironmentConfig.class);
    }
}
