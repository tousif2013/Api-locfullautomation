package in.credable.automation.service.user;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.user.BulkUserFetchRequestVO;
import in.credable.automation.service.vo.user.UserVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public final class UserService {
    private static final Map<String, String> AUTH_HEADERS = HeaderProvider.getAuthHeaders();

    private UserService() {
    }

    public static Response createUser(UserVO createUserRequestVO) {
        return createUser(createUserRequestVO, AUTH_HEADERS, StatusCode.OK);
    }

    public static Response createUser(UserVO createUserRequestVO,
                                      Map<String, String> authHeaders,
                                      StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.IAM_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(authHeaders)
                .body(createUserRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.CREATE_USER_PATH);
    }

    public static Response getUserByUsername(String username) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.IAM_SERVICE_BASE_PATH)
                .headers(AUTH_HEADERS)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .queryParam("username", username)
                .request(Method.GET, EndPoint.CREATE_USER_PATH);
    }

    public static Response getUserById(String userId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.IAM_SERVICE_BASE_PATH)
                .headers(AUTH_HEADERS)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .queryParam("userId", userId)
                .request(Method.GET, EndPoint.CREATE_USER_PATH);
    }

    public static Response updateUser(UserVO updateUserRequestVO) {
        return updateUser(updateUserRequestVO, AUTH_HEADERS, StatusCode.OK);
    }

    public static Response updateUser(UserVO updateUserRequestVO,
                                      Map<String, String> authHeaders,
                                      StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.IAM_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(authHeaders)
                .body(updateUserRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.PUT, EndPoint.CREATE_USER_PATH);
    }

    public static Response deleteUser(String userId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.IAM_SERVICE_BASE_PATH)
                .headers(AUTH_HEADERS)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.DELETE, EndPoint.DELETE_USER_PATH, userId);
    }

    public static Response createUsers(List<UserVO> createUsersRequest) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.IAM_SERVICE_BASE_PATH)
                .headers(AUTH_HEADERS)
                .contentType(ContentType.JSON)
                .body(createUsersRequest)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.CREATE_USERS_PATH);
    }

    public static Response getUsers(BulkUserFetchRequestVO bulkUserFetchRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.IAM_SERVICE_BASE_PATH)
                .headers(AUTH_HEADERS)
                .contentType(ContentType.JSON)
                .body(bulkUserFetchRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.FETCH_USERS_PATH);
    }
}
