package in.credable.automation.service.vo.user;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ADMIN("admin"),
    CLIENT_ADMIN("clientadmin"),
    BORROWER("borrower"),
    CLIENT_USERS("clientusers"),
    CORPORATES("Corporates");


    private final String userRole;

    UserRoleEnum(String userRole) {
        this.userRole = userRole;
    }
}
