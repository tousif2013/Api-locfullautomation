package in.credable.automation.assertions.client;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.client.ClientVO;
import in.credable.automation.service.vo.client.LanguageVO;
import in.credable.automation.service.vo.client.MobileNumberVO;

import java.util.List;
import java.util.Objects;

public final class ClientAssertion extends CustomAssert<ClientAssertion, ClientVO> {
    ClientAssertion(ClientVO actual) {
        super(actual, ClientAssertion.class);
    }

    public ClientAssertion clientIdIsNotNull() {
        // check that actual ClientVO we want to make assertions on is not null
        isNotNull();

        // check assertion logic
        if (Objects.isNull(actual.getId())) {
            failWithMessage("Client id should not be null.");
        }
        // return this to allow chaining other assertion methods
        return this;
    }

    public ClientAssertion hasSameClientId(Long expectedClientId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getId(), expectedClientId, "Client Id");
        return this;
    }

    public ClientAssertion hasSameClientCode(String expectedClientCode) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientCode(), expectedClientCode, "Client Code");
        return this;
    }

    public ClientAssertion hasSameClientName(String expectedClientName) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientName(), expectedClientName, "Client Name");
        return this;
    }

    public ClientAssertion hasSameClientEmail(String expectedClientEmail) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientEmail(), expectedClientEmail, "Client Email");
        return this;
    }

    public ClientAssertion hasSameClientAddress(String expectedClientAddress) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientAddress(), expectedClientAddress, "Client Address");
        return this;
    }

    public ClientAssertion hasSameClientAddress2(String expectedClientAddress2) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientAddress2(), expectedClientAddress2, "Client Address2");
        return this;
    }

    public ClientAssertion hasSameClientCity(String expectedClientCity) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientCity(), expectedClientCity, "Client City");
        return this;
    }

    public ClientAssertion hasSameClientState(String expectedClientState) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientState(), expectedClientState, "Client State");
        return this;
    }

    public ClientAssertion hasSamePinCode(Long pinCode) {
        isNotNull();
        failureWithActualExpectedForNumberComparison(actual.getPinCode(), pinCode, "PIN Code");
        return this;
    }

    public ClientAssertion hasSameCountryId(Long expectedCountryId) {
        isNotNull();
        failureWithActualExpectedForNumberComparison(actual.getCountryId(), expectedCountryId, "Country Id");
        return this;
    }

    public ClientAssertion hasSameOrganizationId(String expectedOrganizationId) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getOrganizationId(), expectedOrganizationId, "Organization Id");
        return this;
    }

    public ClientAssertion hasSameClientUserLoginLink(String expectedClientUserLoginLink) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientUserLoginLink(), expectedClientUserLoginLink, "Client User Login Link");
        return this;
    }

    public ClientAssertion hasSameCorporateUserLoginLink(String expectedCorporateUserLoginLink) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getCorporateUserLoginLink(), expectedCorporateUserLoginLink, "Corporate User Login Link");
        return this;
    }

    public ClientAssertion hasSameClientAdminName(String expectedClientAdminName) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientAdminName(), expectedClientAdminName, "Client Admin Name");
        return this;
    }

    public ClientAssertion hasSameClientAdminEmail(String expectedClientAdminEmail) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientAdminEmail(), expectedClientAdminEmail, "Client Admin Email");
        return this;
    }

    public ClientAssertion hasSameClientAdminMobile(MobileNumberVO expectedClientAdminMobile) {
        isNotNull();
        failureWithActualExpectedForObjectComparison(actual.getClientAdminMobile(), expectedClientAdminMobile, "Client admin mobile");
        return this;
    }

    public ClientAssertion hasSameSpocName(String expectedSpocName) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getSpocName(), expectedSpocName, "SPOC Name");
        return this;
    }

    public ClientAssertion hasSameSpocEmail(String expectedSpocEmail) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getSpocEmail(), expectedSpocEmail, "SPOC Email");
        return this;
    }

    public ClientAssertion hasSameSpocMobile(MobileNumberVO expectedSpocMobile) {
        isNotNull();
        failureWithActualExpectedForObjectComparison(actual.getSpocMobile(), expectedSpocMobile, "SPOC Mobile");
        return this;
    }

    public ClientAssertion hasSameDefaultLanguage(LanguageVO expectedDefaultLanguage) {
        isNotNull();
        failureWithActualExpectedForNumberComparison(actual.getDefaultLanguage().getId(), expectedDefaultLanguage.getId(), "Default language");
        return this;
    }

    public ClientAssertion hasSameLanguages(List<LanguageVO> expectedLanguages) {
        isNotNull();
        failureWithActualExpectedForObjectComparison(actual.getLanguages(), expectedLanguages, "Languages");
        return this;
    }

    public ClientAssertion hasSameLanguageIds(List<LanguageVO> expectedLanguages) {
        isNotNull();
        List<Long> actualLanguageIds = actual.getLanguages().stream().map(LanguageVO::getId).toList();
        List<Long> expectedLanguageIds = expectedLanguages.stream().map(LanguageVO::getId).toList();
        failureWithActualExpectedForObjectComparison(actualLanguageIds, expectedLanguageIds, "Language Ids");
        return this;
    }

    public ClientAssertion clientAdminUserIdIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getClientAdminUserId())) {
            failWithMessage("Client Admin UserId should not be null.");
        }
        return this;
    }

    public ClientAssertion hasSameClientAdminUserId(String expectedClientAdminUserId) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getClientAdminUserId(), expectedClientAdminUserId, "Client Admin UserId");
        return this;
    }
}
