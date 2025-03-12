package in.credable.automation.testcases.core;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.config.EnvironmentConfig;
import in.credable.automation.service.core.AuthenticationService;
import in.credable.automation.service.vo.ErrorResponseVO;
import in.credable.automation.service.vo.bre.BreVO;
import in.credable.automation.service.vo.core.*;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.RandomDataGenerator;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.Test;

public class AuthenticationTest extends BaseTest {
    private static final EnvironmentConfig ENVIRONMENT_CONFIG = ConfigFactory.getEnvironmentConfig();
    private String authenticationId;
    private String twoFactorAuthenticationId;
    private String contactNo;
    private String accessToken;
    private String refreshToken;
    private String password;

    @Test(description = "Test#601-Verify the Send OTP API for pre_signup_borrower.")
    public void verifySendOTP() {
        contactNo = RandomDataGenerator.generateRandomMobileNumber();
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setContactNumber(contactNo);
        BreVO<ValidateOTPVO> breVO = AuthenticationService.sendOTP(validateOTPVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.SEND_OTP_MESSAGE)
                .timeStampIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData()).authenticationIdNotNull();
        authenticationId = breVO.getData().getAuthenticationId();
    }

    @Test(description = "Test#603-Verify the Resend OTP API for borrower.", dependsOnMethods = "verifySendOTP")
    public void verifyReSendOTP() {
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setAuthenticationId(authenticationId);
        BreVO<ValidateOTPVO> breVO = AuthenticationService.reSendOTP(validateOTPVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.SEND_OTP_MESSAGE)
                .timeStampIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData()).authenticationIdNotNull();
        authenticationId = breVO.getData().getAuthenticationId();
    }

    @Test(description = "Test#602-Verify the Validate OTP API for the User .", dependsOnMethods = {"verifyReSendOTP", "verifySendOTP"})
    public void verifyValidateOTP() {
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setAuthenticationId(authenticationId);
        validateOTPVO.setContactNumber(contactNo);
        validateOTPVO.setOtp(ENVIRONMENT_CONFIG.getOTP());
        BreVO<AuthenticationDataResponseVO> breVO = AuthenticationService.validateOTP(validateOTPVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.VALIDATE_OTP_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData()).signUpRequiredisNotNull();
        FrameworkAssertions.assertThat(breVO.getData().getTokenVO())
                .accessTokenIsNotNull()
                .refreshTokenIsNotNull();

        accessToken = breVO.getData().getTokenVO().getAccessToken();
    }

    @Test(description = "Test#616-Verify the Sign-Up API of the borrower .", dependsOnMethods = {"verifyReSendOTP", "verifySendOTP", "verifyValidateOTP"})
    public void verifySignUpNewUser() {
        SignUpVO signUpVO = DataProviderUtil.manufacturePojo(SignUpVO.class);
        signUpVO.setUserId("");
        BreVO<AuthenticationDataResponseVO> breVO = AuthenticationService.signUp(signUpVO, accessToken).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.SIGNUP_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData().getTokenVO())
                .accessTokenIsNotNull()
                .refreshTokenIsNotNull();
        refreshToken = breVO.getData().getTokenVO().getRefreshToken();
    }

    @Test(description = "Test#617-Verify the Logout API for the borrower after signup." +
            "Test#639-Verify the Logout API for the borrower after signup.",
            dependsOnMethods = {"verifySendOTP", "verifyReSendOTP", "verifyValidateOTP", "verifySignUpNewUser"})
    public void verifyLogOutAfterSignUp() {
        BreVO<Object> breVO = AuthenticationService.logout(refreshToken).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.LOGOUT_MESSAGE)
                .timeStampIsNotNull();
        /*639*/
        BreVO<Object> breVO1 = AuthenticationService.logout(refreshToken).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.LOGOUT_MESSAGE)
                .timeStampIsNotNull();
    }


    @Test(description = "Test#628-Verify the Logout API for the borrower after login .", dependsOnMethods = {"verifySendOTP", "verifyReSendOTP", "verifyValidateOTP", "verifySignUpNewUser"})
    public void verifyLogOutAfterLogin() {
        //Send OTP
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setContactNumber(contactNo);
        BreVO<ValidateOTPVO> breVO = AuthenticationService.sendOTP(validateOTPVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.SEND_OTP_MESSAGE)
                .timeStampIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData()).authenticationIdNotNull();
        authenticationId = breVO.getData().getAuthenticationId();
        //Validate OTP
        validateOTPVO.setAuthenticationId(authenticationId);
        validateOTPVO.setOtp(ENVIRONMENT_CONFIG.getOTP());
        BreVO<AuthenticationDataResponseVO> breVO2 = AuthenticationService.validateOTP(validateOTPVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO2)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.VALIDATE_OTP_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        FrameworkAssertions.assertThat(breVO2.getData()).signUpRequiredisNotNull();
        FrameworkAssertions.assertThat(breVO2.getData().getTokenVO())
                .accessTokenIsNotNull()
                .refreshTokenIsNotNull();
        refreshToken = breVO2.getData().getTokenVO().getRefreshToken();
        //Logout
        BreVO<Object> breVO3 = AuthenticationService.logout(refreshToken).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO3)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.LOGOUT_MESSAGE)
                .timeStampIsNotNull();
    }

    @Test(description = "Test#619-Verify the Admin User Can Login using the username and Password through 'verify-password' API .", priority = 1)
    public void verifyLoginUsingPasswordTwoFA() {
        LoginWithPasswordVO loginWithPasswordVO = new LoginWithPasswordVO();
        loginWithPasswordVO.setUsername(ENVIRONMENT_CONFIG.getAdminUserName2FA());
        loginWithPasswordVO.setPassword(ENVIRONMENT_CONFIG.getAdminPassword2FA());
        BreVO<AuthenticationDataResponseVO> breVO = AuthenticationService.loginWithPassword(loginWithPasswordVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.LOGIN_WITH_PASSWORD_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        twoFactorAuthenticationId = breVO.getData().getTwoFactorAuthenticationId();
    }

    @Test(description = "Test#624-Verify Two Factor Authentication (two-fa/resend-otp) API when user login with Admin (username and Password).", dependsOnMethods = "verifyLoginUsingPasswordTwoFA", priority = 1)
    public void verifyResendOTPUsingTwoFA() {
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setAuthenticationId(twoFactorAuthenticationId);
        BreVO<ValidateOTPVO> breVO = AuthenticationService.resendOTPTwoFA(validateOTPVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.SEND_OTP_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData()).authenticationIdNotNull();
        authenticationId = breVO.getData().getAuthenticationId();
    }

    @Test(description = "Test#626-Verify 2fa validate-otp API for Admin login (username and password)", dependsOnMethods = {"verifyLoginUsingPassword", "verifyResendOTPUsingTwoFA"}, priority = 1)
    public void verifyValidateOTPTwoFA() {
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setAuthenticationId(authenticationId);
        validateOTPVO.setOtp(ENVIRONMENT_CONFIG.getOTP());
        BreVO<TokenVO> breVO = AuthenticationService.validateOTPTwoFA(validateOTPVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.VALIDATE_OTP_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData())
                .accessTokenIsNotNull()
                .refreshTokenIsNotNull();
        refreshToken = breVO.getData().getRefreshToken();
    }

    @Test(description = "Test#653-Verify the  'logout' api for 2FA admin user",
            priority = 1, dependsOnMethods = {"verifyLoginUsingPassword", "verifyLoginUsingPasswordTwoFA", "verifyValidateOTPTwoFA", "verifyResendOTPUsingTwoFA"})
    public void verifyLogoutAdminUsing2FA() {
        BreVO breVO = AuthenticationService.logout(refreshToken).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.LOGOUT_MESSAGE)
                .timeStampIsNotNull();
    }

    @Test(description = "Test#807-Verify the Admin User Can Login using the username and Password through 'verify-password' API ", priority = 2)
    public void verifyLoginUsingPassword() {
        LoginWithPasswordVO loginWithPasswordVO = new LoginWithPasswordVO();
        loginWithPasswordVO.setUsername(ENVIRONMENT_CONFIG.getAdminUserName());
        loginWithPasswordVO.setPassword(ENVIRONMENT_CONFIG.getAdminPassword());

        BreVO<AuthenticationDataResponseVO> breVO = AuthenticationService.loginWithPassword(loginWithPasswordVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.LOGIN_WITH_PASSWORD_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData().getTokenVO())
                .refreshTokenIsNotNull()
                .accessTokenIsNotNull();
        refreshToken = breVO.getData().getTokenVO().getRefreshToken();
    }

    @Test(description = "Test#632-Verify the Logout API for the Admin after logged in with username and password ",
            priority = 2, dependsOnMethods = "verifyLoginUsingPassword")
    public void verifyLogoutAdminUsingPassword() {
        BreVO breVO = AuthenticationService.logout(refreshToken).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.LOGOUT_MESSAGE)
                .timeStampIsNotNull();
    }

    @Test(description = "Test#680-Verify Two Factor Authentication for Borrower when Login ", priority = 3)
    public void verifyLoginBorrowerTwoFA() {

        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setContactNumber(ENVIRONMENT_CONFIG.getBorrower2FAMobileNo());
        BreVO<ValidateOTPVO> breVO = AuthenticationService.sendOTP(validateOTPVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.SEND_OTP_MESSAGE)
                .timeStampIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData()).authenticationIdNotNull();
        String authenticationId = breVO.getData().getAuthenticationId();
        //Validate OTP
        ValidateOTPVO validateOTPVO2 = new ValidateOTPVO();
        validateOTPVO2.setAuthenticationId(authenticationId);
        validateOTPVO2.setContactNumber(ENVIRONMENT_CONFIG.getBorrower2FAMobileNo());
        validateOTPVO2.setOtp(ENVIRONMENT_CONFIG.getOTP());
        BreVO<AuthenticationDataResponseVO> breVO2 = AuthenticationService.validateOTP(validateOTPVO2).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO2)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.VALIDATE_OTP_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        FrameworkAssertions.assertThat(breVO2.getData()).signUpRequiredisNotNull();
        FrameworkAssertions.assertThat(breVO2.getData())
                .signUpRequiredisNotNull()
                .twoFactorAuthenticationIdisNotNull();

        String TwoFactorAuthenticationId = breVO2.getData().getTwoFactorAuthenticationId();
        //2FA validate OTP
        ValidateOTPVO validateOTPVO3 = new ValidateOTPVO();
        validateOTPVO3.setAuthenticationId(TwoFactorAuthenticationId);
        validateOTPVO3.setOtp(ENVIRONMENT_CONFIG.getOTP());
        BreVO<TokenVO> breVO3 = AuthenticationService.validateOTPTwoFA(validateOTPVO3).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO3)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.VALIDATE_OTP_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        FrameworkAssertions.assertThat(breVO3.getData())
                .accessTokenIsNotNull()
                .refreshTokenIsNotNull();
    }

    @Test(description = "Test#629-Verify admin user should able to sent the OTP using 'password/reset/send-otp' API with username and password",
            priority = 4)
    public void verifyPasswordResetSendOTP() {
        LoginWithPasswordVO loginWithPasswordVO = new LoginWithPasswordVO();
        loginWithPasswordVO.setUsername(ENVIRONMENT_CONFIG.getAdminUserNameForResetpassword());
        loginWithPasswordVO.setPassword("");
        BreVO<ValidateOTPVO> breVO = AuthenticationService.passwordResetSendOTP(loginWithPasswordVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.SEND_OTP_MESSAGE)
                .timeStampIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData())
                .authenticationIdNotNull();
        authenticationId = breVO.getData().getAuthenticationId();
    }

    @Test(description = "Test#645-Verify Admin is able to validate the OTP to reset the password and create new password by reset-validate-otp API",
            priority = 4, dependsOnMethods = "verifyPasswordResetSendOTP")
    public void verifyPasswordResetValidateOTP() {
        password = RandomDataGenerator.generateRandomPassword();
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setAuthenticationId(authenticationId);
        validateOTPVO.setOtp(ENVIRONMENT_CONFIG.getOTP());
        validateOTPVO.setNewPassword(password);
        BreVO breVO = AuthenticationService.passwordResetValidateOTP(validateOTPVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.PASSWORD_RESET_MESSAGE)
                .timeStampIsNotNull();
        //Verify the changed password
        LoginWithPasswordVO loginWithPasswordVO = new LoginWithPasswordVO();
        loginWithPasswordVO.setUsername(ENVIRONMENT_CONFIG.getAdminUserNameForResetpassword());
        loginWithPasswordVO.setPassword(password);
        BreVO breVO1 = AuthenticationService.loginWithPassword(loginWithPasswordVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO1)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.LOGIN_WITH_PASSWORD_MESSAGE)
                .timeStampIsNotNull();
    }

    @Test(description = "Test#623-Verify Admin is able to Update the Password using 'password update' API ",
            priority = 4, dependsOnMethods = {"verifyPasswordResetSendOTP", "verifyPasswordResetValidateOTP"})
    public void verifyPasswordUpdate() {

        String newPassword = RandomDataGenerator.generateRandomPassword();
        LoginWithPasswordVO loginWithPasswordVO = new LoginWithPasswordVO();
        loginWithPasswordVO.setUsername(ENVIRONMENT_CONFIG.getAdminUserNameForResetpassword());
        loginWithPasswordVO.setOldPassword(password);
        loginWithPasswordVO.setNewPassword(newPassword);
        BreVO breVO = AuthenticationService.passwordUpdate(loginWithPasswordVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.PASSWORD_UPDATE_MESSAGE)
                .timeStampIsNotNull();
        //Login to verify the updated password
        LoginWithPasswordVO loginWithPasswordVO2 = new LoginWithPasswordVO();
        loginWithPasswordVO2.setUsername(ENVIRONMENT_CONFIG.getAdminUserNameForResetpassword());
        loginWithPasswordVO2.setPassword(newPassword);
        BreVO breVO1 = AuthenticationService.loginWithPassword(loginWithPasswordVO2).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO1)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.LOGIN_WITH_PASSWORD_MESSAGE)
                .timeStampIsNotNull();
    }

    @Test(description = "Test#682-Verify borrower is able to resend the 2FA otp using 'two-fa/resend-otp' API.",
            priority = 5)
    public void verifyResendOTPTwoFA() {
        //Send OTP
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setContactNumber(ENVIRONMENT_CONFIG.getBorrower2FAMobileNo());
        BreVO<ValidateOTPVO> breVO = AuthenticationService.sendOTP(validateOTPVO).as(new TypeRef<>() {
        });
        authenticationId = breVO.getData().getAuthenticationId();
        //validate OTP
        validateOTPVO.setContactNumber(ENVIRONMENT_CONFIG.getBorrower2FAMobileNo());
        validateOTPVO.setAuthenticationId(authenticationId);
        validateOTPVO.setOtp(ENVIRONMENT_CONFIG.getOTP());
        BreVO<AuthenticationDataResponseVO> breVO1 = AuthenticationService.validateOTP(validateOTPVO).as(new TypeRef<>() {
        });
        twoFactorAuthenticationId = breVO1.getData().getTwoFactorAuthenticationId();

        //Resend OTP
        ValidateOTPVO validateOTPVO2 = new ValidateOTPVO();
        validateOTPVO2.setAuthenticationId(twoFactorAuthenticationId);
        BreVO<ValidateOTPVO> breVO2 = AuthenticationService.resendOTPTwoFA(validateOTPVO2).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO2)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.SEND_OTP_MESSAGE)
                .timeStampIsNotNull();
        FrameworkAssertions.assertThat(breVO2.getData())
                .authenticationIdNotNull();
        authenticationId = breVO2.getData().getAuthenticationId();
    }

    @Test(description = "Test#683-Verify borrower is able to verify the 2FA otp using 'two-fa/validate-otp' API.",
            priority = 5, dependsOnMethods = "verifyResendOTPTwoFA")
    public void verifyOTPTwoFA() {
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setAuthenticationId(authenticationId);
        validateOTPVO.setOtp(ENVIRONMENT_CONFIG.getOTP());
        BreVO<TokenVO> breVO = AuthenticationService.validateOTPTwoFA(validateOTPVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.VALIDATE_OTP_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        FrameworkAssertions.assertThat(breVO.getData())
                .refreshTokenIsNotNull()
                .accessTokenIsNotNull();
    }

    @Test(description = "Test #296 - Verify the functionality of SSO user onboarding and login endpoint API.")
    public void verifySSOLogin() {
        SSOLoginVO ssoLoginVO = DataProviderUtil.manufacturePojo(SSOLoginVO.class);
        BreVO<TokenVO> breVO = AuthenticationService.ssoLogin(ssoLoginVO, ENVIRONMENT_CONFIG.getSsoEncryptionKeyHex())
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(breVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.TOKEN_GENERATED_SUCCESSFULLY)
                .timeStampIsNotNull()
                .dataIsNotNull();

        FrameworkAssertions.assertThat(breVO.getData())
                .accessTokenIsNotNull()
                .refreshTokenIsNotNull();
    }

    @Test(description = "Test#638-Verify the validation of Sign-Up API of the borrower .", priority = 6)
    public void verifySignUpWithInvalidDetail() {
        /*Send OTP*/
        contactNo = RandomDataGenerator.generateRandomMobileNumber();
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setContactNumber(contactNo);
        BreVO<ValidateOTPVO> breVO = AuthenticationService.sendOTP(validateOTPVO).as(new TypeRef<>() {
        });
        /*Validate OTP*/
        validateOTPVO.setAuthenticationId(breVO.getData().getAuthenticationId());
        validateOTPVO.setOtp(ENVIRONMENT_CONFIG.getOTP());
        BreVO<AuthenticationDataResponseVO> breVO2 = AuthenticationService.validateOTP(validateOTPVO).as(new TypeRef<>() {
        });
        /*without firstName in the request*/
        SignUpVO signUpVO = DataProviderUtil.manufacturePojo(SignUpVO.class);
        signUpVO.setUserId("");
        signUpVO.setFirstName("");
        ErrorResponseVO breVO3 = AuthenticationService.signUp(signUpVO, breVO2.getData().getTokenVO().getAccessToken(), StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO3)
                .hasSameErrorMessage("firstName must not be blank")
                .timestampIsNotNull();
        /*without lastName in the request*/
        SignUpVO signUpVO2 = DataProviderUtil.manufacturePojo(SignUpVO.class);
        signUpVO2.setUserId("");
        signUpVO2.setLastName("");
        ErrorResponseVO breVO4 = AuthenticationService.signUp(signUpVO2, breVO2.getData().getTokenVO().getAccessToken(), StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO4)
                .hasSameErrorMessage("lastName must not be blank")
                .timestampIsNotNull();
        /*without email in the request*/
        SignUpVO signUpVO3 = DataProviderUtil.manufacturePojo(SignUpVO.class);
        signUpVO3.setUserId("");
        signUpVO3.setEmailId("");
        ErrorResponseVO breVO5 = AuthenticationService.signUp(signUpVO3, breVO2.getData().getTokenVO().getAccessToken(), StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO5)
                .hasSameErrorMessage("emailId must not be blank")
                .timestampIsNotNull();
        /*All field as blank*/
        SignUpVO signUpVO4 = DataProviderUtil.manufacturePojo(SignUpVO.class);
        signUpVO4.setUserId("");
        signUpVO4.setEmailId("");
        signUpVO4.setFirstName("");
        signUpVO4.setLastName("");
        ErrorResponseVO breVO6 = AuthenticationService.signUp(signUpVO4, breVO2.getData().getTokenVO().getAccessToken(), StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(breVO6)
                .containsErrorMessage("firstName must not be blank")
                .containsErrorMessage("emailId must not be blank")
                .containsErrorMessage("lastName must not be blank")
                .timestampIsNotNull();

        /*all the mandatory fields */
        SignUpVO signUpVO5 = DataProviderUtil.manufacturePojo(SignUpVO.class);
        signUpVO5.setUserId("");
        BreVO<AuthenticationDataResponseVO> breVO7 = AuthenticationService.signUp(signUpVO5, breVO2.getData().getTokenVO().getAccessToken()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO7)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.SIGNUP_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();
        FrameworkAssertions.assertThat(breVO7.getData().getTokenVO())
                .accessTokenIsNotNull()
                .refreshTokenIsNotNull();
        /*Duplicate entry check*/
        /*Send OTP*/
        ValidateOTPVO validateOTPVO2 = new ValidateOTPVO();
        validateOTPVO2.setContactNumber(RandomDataGenerator.generateRandomMobileNumber());
        BreVO<ValidateOTPVO> breVO1 = AuthenticationService.sendOTP(validateOTPVO2).as(new TypeRef<>() {
        });
        /*Validate OTP*/
        validateOTPVO2.setAuthenticationId(breVO1.getData().getAuthenticationId());
        validateOTPVO2.setOtp(ENVIRONMENT_CONFIG.getOTP());
        /*validate otp*/
        BreVO<AuthenticationDataResponseVO> breVO8 = AuthenticationService.validateOTP(validateOTPVO2).as(new TypeRef<>() {
        });
        /*Same request body with different access token*/
        ErrorResponseVO breVO9 = AuthenticationService.signUp(signUpVO5, breVO8.getData().getTokenVO().getAccessToken(), StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO9)
                .hasSameErrorMessage("Duplicate Username or Email Id or Mobile Number")
                .timestampIsNotNull()
                .hasSameErrorCode("IAM007");
    }

    @Test(description = "Test#636-Verify the validation of Validate OTP API .", priority = 6)
    public void verifyValidateOTPWithInvalidDetail() {
        /*Send OTP*/
        contactNo = RandomDataGenerator.generateRandomMobileNumber();
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setContactNumber(contactNo);
        BreVO<ValidateOTPVO> breVO = AuthenticationService.sendOTP(validateOTPVO).as(new TypeRef<>() {
        });
        /*Validate OTP without authenticationId in request*/
        ;
        ValidateOTPVO validateOTPVOWithOutAuthId = new ValidateOTPVO();
        validateOTPVOWithOutAuthId.setAuthenticationId(null);
        validateOTPVOWithOutAuthId.setContactNumber(contactNo);
        validateOTPVOWithOutAuthId.setOtp(ENVIRONMENT_CONFIG.getOTP());
        ErrorResponseVO errorResponseVO = AuthenticationService.validateOTP(validateOTPVOWithOutAuthId, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorMessage("authenticationId must not be blank")
                .timestampIsNotNull();
        /*Validate OTP with invalid authenticationId in request*/
        ;
        validateOTPVOWithOutAuthId.setAuthenticationId(RandomDataGenerator.getUuid());
        errorResponseVO = AuthenticationService.validateOTP(validateOTPVOWithOutAuthId, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorMessage(ApiMessageConstants.INVALID_AUTH_ID)
                .timestampIsNotNull();
        /*Validate OTP without contactNo in request*/
        ;
        validateOTPVOWithOutAuthId.setAuthenticationId(breVO.getData().getAuthenticationId());
        validateOTPVOWithOutAuthId.setContactNumber("");
        errorResponseVO = AuthenticationService.validateOTP(validateOTPVOWithOutAuthId, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorMessage("contactNumber must not be blank")
                .timestampIsNotNull();
        /*Validate OTP with invalid OTP in request*/
        ;
        validateOTPVOWithOutAuthId.setContactNumber(contactNo);
        validateOTPVOWithOutAuthId.setOtp(RandomDataGenerator.generateRandomNumber(6).toString());
        errorResponseVO = AuthenticationService.validateOTP(validateOTPVOWithOutAuthId, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorMessage(ApiMessageConstants.INCORRECT_OTP)
                .timestampIsNotNull();
        /*Validate OTP with without OTP in request*/
        ;
        validateOTPVOWithOutAuthId.setOtp(null);
        errorResponseVO = AuthenticationService.validateOTP(validateOTPVOWithOutAuthId, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorMessage("otp must not be blank")
                .timestampIsNotNull();
        /*Valid*/
        validateOTPVOWithOutAuthId.setOtp(ENVIRONMENT_CONFIG.getOTP());
        BreVO<AuthenticationDataResponseVO> breVO2 = AuthenticationService.validateOTP(validateOTPVOWithOutAuthId).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO2)
                .dataIsNotNull()
                .hasSameMessage(ApiMessageConstants.VALIDATE_OTP_MESSAGE)
                .timeStampIsNotNull()
                .hasSameResponseCode("SUCCESS");
        /*Send request multiple time*/
        ErrorResponseVO errorResponseVO1 = AuthenticationService.validateOTP(validateOTPVOWithOutAuthId, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO1)
                .hasSameErrorMessage("OTP is already verified")
                .timestampIsNotNull();
    }

    @Test(description = "Test#637-Verify the Validation of resend-otp API for borrower.", priority = 7)
    public void verifyResendOtpWithInvalidDetail() {
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setAuthenticationId(RandomDataGenerator.getUuid());
        /*Invalid authId*/
        ErrorResponseVO errorResponseVO = AuthenticationService.reSendOTP(validateOTPVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorMessage(ApiMessageConstants.INVALID_AUTH_ID)
                .hasSameErrorCode("IAM010")
                .timestampIsNotNull();
        /*Without authId*/
        validateOTPVO.setAuthenticationId(null);
        ErrorResponseVO errorResponseVO2 = AuthenticationService.reSendOTP(validateOTPVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO2)
                .hasSameErrorMessage("authenticationId must not be blank")
                .timestampIsNotNull();
    }

    @Test(description = "Test#646-Verify the validation of 'reset-verify-otp' API to reset the Admin Password.", priority = 7)
    public void verifyResetVerifyOTP() {
        LoginWithPasswordVO loginWithPasswordVO = new LoginWithPasswordVO();
        loginWithPasswordVO.setUsername(ENVIRONMENT_CONFIG.getAdminUserNameForResetpassword());
        BreVO<ValidateOTPVO> breVO = AuthenticationService.passwordResetSendOTP(loginWithPasswordVO).as(new TypeRef<>() {
        });
        /*Invalid OTP*/
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setAuthenticationId(breVO.getData().getAuthenticationId());
        validateOTPVO.setOtp(RandomDataGenerator.generateRandomNumber(6).toString());
        validateOTPVO.setNewPassword(RandomDataGenerator.generateRandomPassword());
        ErrorResponseVO errorResponseVO = AuthenticationService.passwordResetValidateOTP(validateOTPVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("AUTH010")
                .timestampIsNotNull()
                .hasSameErrorMessage(ApiMessageConstants.INCORRECT_OTP);
        /*Invalid authenticationId*/
        ValidateOTPVO validateOTPVO2 = new ValidateOTPVO();
        validateOTPVO2.setAuthenticationId(RandomDataGenerator.getUuid());
        validateOTPVO2.setOtp(ENVIRONMENT_CONFIG.getOTP());
        validateOTPVO2.setNewPassword(RandomDataGenerator.generateRandomPassword());
        ErrorResponseVO errorResponseVO2 = AuthenticationService.passwordResetValidateOTP(validateOTPVO2, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO2)
                .timestampIsNotNull()
                .hasSameErrorMessage(ApiMessageConstants.INVALID_AUTH_ID);
        /*without AuthID*/
        validateOTPVO2.setAuthenticationId(null);
        ErrorResponseVO errorResponseVO3 = AuthenticationService.passwordResetValidateOTP(validateOTPVO2, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO3)
                .timestampIsNotNull()
                .hasSameErrorMessage(ApiMessageConstants.AUTHENTICATIONID_MUST_NOT_NULL);
        /*WIthout OTP*/
        ValidateOTPVO validateOTPVO3 = new ValidateOTPVO();
        validateOTPVO3.setAuthenticationId(RandomDataGenerator.getUuid());
        validateOTPVO3.setOtp(null);
        validateOTPVO3.setNewPassword(RandomDataGenerator.generateRandomPassword());
        ErrorResponseVO errorResponseVO4 = AuthenticationService.passwordResetValidateOTP(validateOTPVO3, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO4)
                .timestampIsNotNull()
                .hasSameErrorMessage(ApiMessageConstants.OTP_MUST_NOT_NULL);
        /*Without password*/
        ValidateOTPVO validateOTPVO4 = new ValidateOTPVO();
        validateOTPVO4.setAuthenticationId(RandomDataGenerator.getUuid());
        validateOTPVO4.setOtp(ENVIRONMENT_CONFIG.getOTP());
        validateOTPVO4.setNewPassword(null);
        ErrorResponseVO errorResponseVO5 = AuthenticationService.passwordResetValidateOTP(validateOTPVO4, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO5)
                .timestampIsNotNull()
                .hasSameErrorMessage("newPassword must not be blank");
        /*Valid*/
        ValidateOTPVO validateOTPVO5 = new ValidateOTPVO();
        validateOTPVO5.setAuthenticationId(breVO.getData().getAuthenticationId());
        validateOTPVO5.setOtp(ENVIRONMENT_CONFIG.getOTP());
        validateOTPVO5.setNewPassword(RandomDataGenerator.generateRandomPassword());
        BreVO breVO1 = AuthenticationService.passwordResetValidateOTP(validateOTPVO5).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(breVO1)
                .hasSameResponseCode("SUCCESS")
                .hasSameMessage(ApiMessageConstants.PASSWORD_RESET_MESSAGE)
                .timeStampIsNotNull();
        /*again send the request to 'reset/validate-otp' api with same authentication and OTP*/
        ErrorResponseVO errorResponseVO1 = AuthenticationService.passwordResetValidateOTP(validateOTPVO5, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO1)
                .hasSameErrorMessage("OTP is already verified")
                .timestampIsNotNull();
    }

    @Test(description = "Test#641-Verify the validation of password/reset/send-otp API of Admin.",
            priority = 7)
    public void verifyPasswordResetSendOTPWithInvalidInput() {
        /*Invalid UserName*/
        LoginWithPasswordVO loginWithPasswordVO = new LoginWithPasswordVO();
        loginWithPasswordVO.setUsername(RandomDataGenerator.generateRandomMobileNumber());
        ErrorResponseVO errorResponseVO = AuthenticationService.passwordResetSendOTP(loginWithPasswordVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .timestampIsNotNull()
                .hasSameErrorCode("IAM003")
                .containsErrorMessage(ApiMessageConstants.USER_NOT_FOUND);
        /*Blank UserName*/
        loginWithPasswordVO.setUsername("");
        loginWithPasswordVO.setPassword(RandomDataGenerator.generateRandomPassword());
        ErrorResponseVO errorResponseVO2 = AuthenticationService.passwordResetSendOTP(loginWithPasswordVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO2)
                .timestampIsNotNull()
                .containsErrorMessage("username must not be blank");
    }

    @Test(description = "Test#666-Verify Error Message should displayed to Admin when send request to 'reset/send-opt' api multiple times with in 10 minutes .",
            priority = 7)
    public void verifyResetSendOTPMultipleTime() {
        LoginWithPasswordVO loginWithPasswordVO = new LoginWithPasswordVO();
        loginWithPasswordVO.setUsername(ENVIRONMENT_CONFIG.getAdminUserNameForMultipleSendOtp());
        loginWithPasswordVO.setPassword(null);
        //ErrorResponseVO errorResponseVO = null;
        for (int i = 0; i < 3; i++) {
            AuthenticationService.passwordResetSendOTP(loginWithPasswordVO).as(new TypeRef<>() {
            });
        }
        ErrorResponseVO errorResponseVO = AuthenticationService.passwordResetSendOTP(loginWithPasswordVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .timestampIsNotNull()
                .containsErrorMessage("Too many request to send OTP! Please try again after 10 minutes");
    }

    @Test(description = "Test#651-Verify Error Message should displayed to Admin when send request to 'reset/send-opt' api multiple times with in 10 minutes .",
            priority = 7)
    public void verifyTwoFactorAuthenticatonIdWithInvalidInput() {
        ValidateOTPVO validateOTPVO = new ValidateOTPVO();
        validateOTPVO.setAuthenticationId(RandomDataGenerator.getUuid());

        ErrorResponseVO errorResponseVO = AuthenticationService.resendOTPTwoFA(validateOTPVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorMessage(ApiMessageConstants.INVALID_AUTH_ID)
                .timestampIsNotNull();
        /*Without Authentication id*/
        validateOTPVO.setAuthenticationId("");
        ErrorResponseVO errorResponseVO2 = AuthenticationService.resendOTPTwoFA(validateOTPVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorMessage(ApiMessageConstants.INVALID_AUTH_ID)
                .timestampIsNotNull();

    }

    @Test(description = "Test#693-Verify the Validation of 'password/update' API .",
            priority = 7)
    public void verifyPasswordUpdateWithInvalidData() {
        /*WIthout new Password*/
        LoginWithPasswordVO loginWithPasswordVO = new LoginWithPasswordVO();
        loginWithPasswordVO.setUsername(ENVIRONMENT_CONFIG.getAdminUserNameForResetpassword());
        loginWithPasswordVO.setOldPassword(ENVIRONMENT_CONFIG.getAdminPassword());
        ErrorResponseVO errorResponseVO = AuthenticationService.passwordUpdate(loginWithPasswordVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorMessage("newPassword must not be blank")
                .timestampIsNotNull();
        /*WIthout old Password*/
        loginWithPasswordVO.setOldPassword(null);
        loginWithPasswordVO.setNewPassword(RandomDataGenerator.generateRandomPassword());
        ErrorResponseVO errorResponseVO2 = AuthenticationService.passwordUpdate(loginWithPasswordVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO2)
                .hasSameErrorMessage("oldPassword must not be blank")
                .timestampIsNotNull();
        /*Without userName*/
        loginWithPasswordVO.setOldPassword(RandomDataGenerator.generateRandomPassword());
        loginWithPasswordVO.setUsername(null);
        ErrorResponseVO errorResponseVO3 = AuthenticationService.passwordUpdate(loginWithPasswordVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO3)
                .hasSameErrorMessage("username must not be blank")
                .timestampIsNotNull();
        /*All the field as blank*/
        loginWithPasswordVO.setOldPassword(null);
        loginWithPasswordVO.setNewPassword(null);
        ErrorResponseVO errorResponseVO4 = AuthenticationService.passwordUpdate(loginWithPasswordVO, StatusCode.BAD_REQUEST).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(errorResponseVO4)
                .containsErrorMessage("username must not be blank")
                .containsErrorMessage("oldPassword must not be blank")
                .containsErrorMessage("newPassword must not be blank")
                .timestampIsNotNull();

    }
}