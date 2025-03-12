package in.credable.automation.assertions.bre;

import in.credable.automation.service.vo.bre.BreVO;
import in.credable.automation.service.vo.core.AuthenticationDataResponseVO;
import in.credable.automation.service.vo.core.TokenVO;
import in.credable.automation.service.vo.core.ValidateOTPVO;

public final class BREAssertionsFactory {
    private BREAssertionsFactory() {
    }

    public static <T> BREAssertion<T> createBREAssertion(BreVO<T> actual) {
        return new BREAssertion<T>(actual);
    }

    public static ValidateOTPAssertion createValidateOTPAssertion(ValidateOTPVO actual) {
        return new ValidateOTPAssertion(actual);
    }

    public static AuthenticationDataResponseAssertion createAuthenticationDataResponseAssertion(AuthenticationDataResponseVO actual) {
        return new AuthenticationDataResponseAssertion(actual);
    }

    public static TokenAssertion createTokenAssertion(TokenVO actual) {
        return new TokenAssertion(actual);
    }
}
