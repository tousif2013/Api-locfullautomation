package in.credable.automation.service.core;

import in.credable.automation.commons.utils.EncryptionUtil;
import in.credable.automation.commons.utils.SerializationUtil;
import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.core.LoginWithPasswordVO;
import in.credable.automation.service.vo.core.SSOLoginVO;
import in.credable.automation.service.vo.core.SignUpVO;
import in.credable.automation.service.vo.core.ValidateOTPVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class AuthenticationService {

    private AuthenticationService() {
    }

    /**
     * Used to Send the OTP
     *
     * @param validateOTPVO
     * @return {@link Response} object containing response body as {@code BreVO<ValidateOTPVO>}
     */
    public static Response sendOTP(ValidateOTPVO validateOTPVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(validateOTPVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.SEND_OTP_PATH);
    }

    /**
     * Used to resend the otp
     *
     * @param validateOTPVO
     * @return {@link Response} object containing response body as {@code BreVO<ValidateOTPVO>}
     */
    public static Response reSendOTP(ValidateOTPVO validateOTPVO) {
        return reSendOTP(validateOTPVO,StatusCode.OK);
    }
    /**
     * Used to resend the otp
     *
     * @param validateOTPVO
     * @param statusCode
     * @return {@link Response} object containing response body as {@code BreVO<ValidateOTPVO>}
     */
    public static Response reSendOTP(ValidateOTPVO validateOTPVO,StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(validateOTPVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.RESEND_OTP_PATH);
    }

    /**
     * Used to validate the otp
     *
     * @param validateOTPVO
     * @return {@link Response} object containing response body as {@code BreVO<AuthenticationDataResponseVO>}
     */

    public static Response validateOTP(ValidateOTPVO validateOTPVO) {
        return validateOTP(validateOTPVO,StatusCode.OK);
    }
    /**
     * Used to validate the otp
     *
     * @param validateOTPVO
     * @param statusCode
     * @return {@link Response} object containing response body as {@code BreVO<AuthenticationDataResponseVO>}
     */

    public static Response validateOTP(ValidateOTPVO validateOTPVO,StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(validateOTPVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.VALIDATE_OTP_PATH);
    }

    /**
     * Used to signUp a new user
     *
     * @param signUpVO
     * @param bearerToken
     * @return {@link Response} object containing response body as {@code BreVO<AuthenticationDataResponseVO>}
     */

    public static Response signUp(SignUpVO signUpVO, String bearerToken) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .bearerToken(bearerToken)
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(signUpVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.SIGNUP_PATH);
    }
    /**
     * Used to signUp a new user
     *
     * @param signUpVO
     * @param bearerToken
     * @param statusCode
     * @return {@link Response} object containing response body as {@code BreVO<AuthenticationDataResponseVO>}
     */

    public static Response signUp(SignUpVO signUpVO, String bearerToken,StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .bearerToken(bearerToken)
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(signUpVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.SIGNUP_PATH);
    }

    /**
     * Used to logout the user
     *
     * @param bearerToken
     * @return {@link Response} object containing response body as {@code BreVO<Object>}
     */

    public static Response logout(String bearerToken) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .header("X-REFRESH-TOKEN", bearerToken)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.LOGOUT_PATH);
    }
    /**
     * Used to logout the user
     *
     * @param bearerToken
     * @param statusCode
     * @return {@link Response} object containing response body as {@code BreVO<Object>}
     */

    public static Response logout(String bearerToken,StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .header("X-REFRESH-TOKEN", bearerToken)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.LOGOUT_PATH);
    }

    /**
     * Used to login the user with password
     *
     * @param loginWithPasswordVO
     * @return {@link Response} object containing response body as {@code BreVO<AuthenticationDataResponseVO>}
     */

    public static Response loginWithPassword(LoginWithPasswordVO loginWithPasswordVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(loginWithPasswordVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.LOGIN_WITH_PASSWORD_PATH);
    }

    /**
     * Used to resend the OTP for two factor authentication
     *
     * @param validateOTPVO
     * @return {@link Response} object containing response body as {@code BreVO<ValidateOTPVO>}
     */

    public static Response resendOTPTwoFA(ValidateOTPVO validateOTPVO) {
        return resendOTPTwoFA(validateOTPVO,StatusCode.OK);
    }
    /**
     * Used to resend the OTP for two factor authentication
     *
     * @param validateOTPVO
     * @param statusCode
     * @return {@link Response} object containing response body as {@code BreVO<ValidateOTPVO>}
     */

    public static Response resendOTPTwoFA(ValidateOTPVO validateOTPVO,StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(validateOTPVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.RESEND_OTP_2FA_PATH);
    }

    /**
     * Used to validate the OTP for two factor authentication
     *
     * @param validateOTPVO
     * @return {@link Response} object containing response body as {@code BreVO<TokenVO>}
     */
    public static Response validateOTPTwoFA(ValidateOTPVO validateOTPVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(validateOTPVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.VALIDATE_OTP_2FA_PATH);
    }

    /**
     * Used to send OTP for password reset
     *
     * @param loginWithPasswordVO
     * @return {@link Response} object containing response body as {@code BreVO<ValidateOTPVO>}
     */
    public static Response passwordResetSendOTP(LoginWithPasswordVO loginWithPasswordVO) {
        return passwordResetSendOTP(loginWithPasswordVO,StatusCode.OK);
    }
    /**
     * Used to send OTP for password reset
     *
     * @param loginWithPasswordVO
     * @param statusCode
     * @return {@link Response} object containing response body as {@code BreVO<ValidateOTPVO>}
     */
    public static Response passwordResetSendOTP(LoginWithPasswordVO loginWithPasswordVO,StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(loginWithPasswordVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.PASSWORD_RESET_SEND_OTP_PATH);
    }

    /**
     * Used to validate OTP for password reset
     *
     * @param validateOTPVO
     * @return {@link Response} object containing response body as {@code BreVO<ValidateOTPVO>}
     */
    public static Response passwordResetValidateOTP(ValidateOTPVO validateOTPVO) {
        return passwordResetValidateOTP(validateOTPVO,StatusCode.OK);
    }
    /**
     * Used to validate OTP for password reset
     *
     * @param validateOTPVO
     * @param statusCode
     * @return {@link Response} object containing response body as {@code BreVO<ValidateOTPVO>}
     */
    public static Response passwordResetValidateOTP(ValidateOTPVO validateOTPVO,StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(validateOTPVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.PASSWORD_RESET_VALIDATE_OTP_PATH);
    }

    /**
     * Used to update the password
     *
     * @param loginWithPasswordVO
     * @return {@link Response} object containing response body as {@code BreVO}
     */
    public static Response passwordUpdate(LoginWithPasswordVO loginWithPasswordVO) {
        return passwordUpdate(loginWithPasswordVO,StatusCode.OK);
    }
    /**
     * Used to update the password
     *
     * @param loginWithPasswordVO
     * @param statusCode
     * @return {@link Response} object containing response body as {@code BreVO}
     */
    public static Response passwordUpdate(LoginWithPasswordVO loginWithPasswordVO,StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .body(loginWithPasswordVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.PASSWORD_UPDATE_PATH);
    }


    /**
     * Used to log in the user using SSO
     *
     * @param ssoLoginVO          The {@link SSOLoginVO} object
     * @param ssoEncryptionKeyHex The sso encryption key in hex format
     * @return {@link Response} object containing response body as {@code BreVO<TokenVO>}
     */
    public static Response ssoLogin(SSOLoginVO ssoLoginVO, String ssoEncryptionKeyHex) {
        log.debug("ssoLoginVO : {}", ssoLoginVO);
        String jsonData = SerializationUtil.serialize(ssoLoginVO);
        log.debug("ssoLoginVO to JSON: {}", jsonData);
        String ssoCode = EncryptionUtil.encrypt(jsonData, ssoEncryptionKeyHex);
        log.debug("ssoCode : {}", ssoCode);
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getUserAuthenticationHeaders())
                .queryParam("code", ssoCode)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.SSO_LOGIN_PATH);
    }
}