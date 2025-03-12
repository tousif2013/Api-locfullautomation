package in.credable.automation.assertions.client;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.client.ClientThemeVO;

public final class ClientThemeAssertion extends CustomAssert<ClientThemeAssertion, ClientThemeVO> {
    ClientThemeAssertion(ClientThemeVO actual) {
        super(actual, ClientThemeAssertion.class);
    }

    public ClientThemeAssertion idIsNotNull() {
        isNotNull();
        super.propertyIsNotNull(actual.getId(), "Id");
        return this;
    }

    public ClientThemeAssertion clientIdIs(Long clientId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getClientId(), clientId, "clientId");
        return this;
    }


    public ClientThemeAssertion headerIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getHeader(), expected, "header");
        return this;
    }

    public ClientThemeAssertion footerIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getFooter(), expected, "footer");
        return this;
    }

    public ClientThemeAssertion buttonIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getButton(), expected, "button");
        return this;
    }

    public ClientThemeAssertion brandIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getBrand(), expected, "brand");
        return this;
    }

}
