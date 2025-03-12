package in.credable.automation.service.auth;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.auth.TokenVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class AuthService {
    private AuthService() {
    }

    public static Response getDirectGrant(String username) {
        return RestAssuredClient
                .withDefaultBaseUrl()
                .enableLoggingFilters(true, false)
                .basePath(EndPoint.IAM_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getAuthHeaders())
                .queryParam("username", username)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.DIRECT_GRANT_PATH);
    }

    public static String getAccessToken(String username) {
        return getDirectGrant(username)
                .as(new TypeRef<ResponseWO<TokenVO>>() {
                }).getData()
                .getAccessToken();
    }
}
