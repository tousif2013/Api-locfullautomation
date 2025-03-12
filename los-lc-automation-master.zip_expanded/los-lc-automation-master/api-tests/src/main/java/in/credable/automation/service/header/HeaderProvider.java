package in.credable.automation.service.header;

import in.credable.automation.config.ConfigFactory;
import in.credable.automation.config.EnvironmentConfig;
import in.credable.automation.utils.RandomDataGenerator;

import java.util.HashMap;
import java.util.Map;

public final class HeaderProvider {

    private static final Map<String, String> AUTH_HEADERS = new HashMap<>(3);
    private static final EnvironmentConfig ENVIRONMENT_CONFIG = ConfigFactory.getEnvironmentConfig();
    private static final Map<String, String> INTEGRATION_SERVICE_HEADERS = new HashMap<>(2);
    private static final Map<String, String> EXECUTE_BUSINESS_RULE_HEADERS = new HashMap<>(3);
    private static final Map<String, String> USER_AUTHENTICATION_HEADER = new HashMap<>(1);
    private static final Map<String, String> LOS_CORE_HEADERS = new HashMap<>(1);

    private HeaderProvider() {
    }

    public static Map<String, String> getAuthHeaders() {
        if (AUTH_HEADERS.isEmpty()) {
            AUTH_HEADERS.put(HeaderName.ORG_ID, ENVIRONMENT_CONFIG.getKeyCloakOrganizationId());
            AUTH_HEADERS.put(HeaderName.X_CLIENT_ID, ENVIRONMENT_CONFIG.getKeyCloakClientId());
            AUTH_HEADERS.put(HeaderName.CLIENT_SECRET, ENVIRONMENT_CONFIG.getKeyCloakClientSecret());
        }
        return AUTH_HEADERS;
    }

    public static Map<String, String> getDocumentVaultUploadRequestHeaders() {
        Map<String, String> headers = new HashMap<>(3);
        headers.put(HeaderName.TRANSACTION_ID, RandomDataGenerator.getUuid());
        headers.put(HeaderName.SOURCE, "api-automation");
        headers.put(HeaderName.CLIENT_ID, "1");
        return headers;
    }

    public static Map<String, String> getIntegrationServiceHeaders() {
        if (INTEGRATION_SERVICE_HEADERS.isEmpty()) {
            INTEGRATION_SERVICE_HEADERS.put(HeaderName.CLIENT_ID, ENVIRONMENT_CONFIG.getIntegrationClientId());
            INTEGRATION_SERVICE_HEADERS.put(HeaderName.SOURCE_ID, ENVIRONMENT_CONFIG.getIntegrationSourceId());
        }
        return INTEGRATION_SERVICE_HEADERS;
    }

    public static Map<String, String> getExecuteBusinessRuleHeaders() {
        if (EXECUTE_BUSINESS_RULE_HEADERS.isEmpty()) {
            EXECUTE_BUSINESS_RULE_HEADERS.put(HeaderName.CLIENT_ID, "tyd");
            EXECUTE_BUSINESS_RULE_HEADERS.put(HeaderName.SOURCE, "e56");
            EXECUTE_BUSINESS_RULE_HEADERS.put(HeaderName.TRANSACTION_ID, "3");
        }
        return EXECUTE_BUSINESS_RULE_HEADERS;
    }

    public static Map<String, String> getUserAuthenticationHeaders() {
        if (USER_AUTHENTICATION_HEADER.isEmpty()) {
            USER_AUTHENTICATION_HEADER.put(HeaderName.ORG_ID, ENVIRONMENT_CONFIG.getKeyCloakOrganizationId());
        }
        return USER_AUTHENTICATION_HEADER;
    }

    public static Map<String, String> getLosCoreHeaders() {
        if (LOS_CORE_HEADERS.isEmpty()) {
            LOS_CORE_HEADERS.put(HeaderName.ORG_ID, ENVIRONMENT_CONFIG.getKeyCloakOrganizationId());
        }
        return LOS_CORE_HEADERS;
    }
}
