package in.credable.automation.assertions.bre;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.core.TokenVO;

import java.util.Objects;

public final class TokenAssertion extends CustomAssert<TokenAssertion, TokenVO> {
    TokenAssertion(TokenVO actual) {
        super(actual, TokenAssertion.class);
    }

    public TokenAssertion accessTokenIsNotNull() {
        isNotNull();

        if (Objects.isNull(actual.getAccessToken())) {
            failWithMessage("Access token should not be null.");
        }
        return this;
    }

    public TokenAssertion refreshTokenIsNotNull() {
        isNotNull();

        if (Objects.isNull(actual.getRefreshToken())) {
            failWithMessage("Refresh token should not be null.");
        }
        return this;
    }

}