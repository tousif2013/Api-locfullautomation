package in.credable.automation.assertions.user;

import in.credable.automation.service.vo.user.CreateUserResponseVO;

public final class UserAssertionsFactory {
    private UserAssertionsFactory() {
    }

    public static CreateUserAssertion createUserAssertion(CreateUserResponseVO actual) {
        return new CreateUserAssertion(actual);
    }
}
