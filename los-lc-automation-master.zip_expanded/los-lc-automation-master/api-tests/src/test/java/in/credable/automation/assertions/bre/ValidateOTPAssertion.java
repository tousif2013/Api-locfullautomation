package in.credable.automation.assertions.bre;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.core.ValidateOTPVO;

import java.util.Objects;

public final class ValidateOTPAssertion extends CustomAssert<ValidateOTPAssertion, ValidateOTPVO> {
    ValidateOTPAssertion(ValidateOTPVO actual) {
        super(actual, ValidateOTPAssertion.class);
    }

    public ValidateOTPAssertion authenticationIdNotNull() {
        isNotNull();

        if (Objects.isNull(actual.getAuthenticationId())) {
            failWithMessage("AuthenticationId should not be null.");
        }
        return this;
    }

}