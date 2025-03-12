package in.credable.automation.assertions.client;

import in.credable.automation.service.vo.client.ClientThemeVO;
import in.credable.automation.service.vo.client.ClientVO;

public final class ClientAssertionsFactory {
    private ClientAssertionsFactory() {
    }

    public static ClientAssertion createClientAssertion(ClientVO actual) {
        return new ClientAssertion(actual);
    }

    public static ClientThemeAssertion createClientThemeAssertion(ClientThemeVO actual) {
        return new ClientThemeAssertion(actual);
    }
}
