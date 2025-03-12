package in.credable.automation.assertions.bre;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.core.AuthenticationDataResponseVO;

import java.util.Objects;

public final class AuthenticationDataResponseAssertion extends CustomAssert<AuthenticationDataResponseAssertion, AuthenticationDataResponseVO> {
    AuthenticationDataResponseAssertion(AuthenticationDataResponseVO actual) {
        super(actual, AuthenticationDataResponseAssertion.class);
    }

    public AuthenticationDataResponseAssertion signUpRequiredisNotNull() {
        isNotNull();

        if (Objects.isNull(actual.getSignUpRequired())) {
            failWithMessage("Signup Required should not be null.");
        }
        return this;
    }

    public AuthenticationDataResponseAssertion twoFactorAuthenticationIdisNotNull() {
        isNotNull();

        if (Objects.isNull(actual.getTwoFactorAuthenticationId())) {
            failWithMessage("Two factor AuthenticationId should not be null.");
        }
        return this;
    }
}