package in.credable.automation.assertions.user;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.user.CreateUserResponseVO;

import java.util.Objects;

public final class CreateUserAssertion extends CustomAssert<CreateUserAssertion, CreateUserResponseVO> {
    CreateUserAssertion(CreateUserResponseVO actual) {
        super(actual, CreateUserAssertion.class);
    }

    public CreateUserAssertion userIdIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getUserId())) {
            failWithMessage("User id should not be null.");
        }
        return this;
    }

    public CreateUserAssertion hasSameUserName(String expectedUserName) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getUserName(), expectedUserName, "username");
        return this;
    }
}
