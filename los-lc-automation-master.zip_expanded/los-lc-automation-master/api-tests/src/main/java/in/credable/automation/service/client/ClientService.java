package in.credable.automation.service.client;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.vo.client.ClientFieldMappingVO;
import in.credable.automation.service.vo.client.ClientThemeVO;
import in.credable.automation.service.vo.client.ClientVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.List;

public final class ClientService {
    private ClientService() {
    }

    public static Response createClient(ClientVO clientRequestVO) {
        return createClient(clientRequestVO, StatusCode.OK);
    }

    public static Response createClient(ClientVO clientRequestVO, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(clientRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.CREATE_CLIENT_PATH);
    }

    public static Response getClientDetails(Long clientId) {
        return getClientDetails(clientId, StatusCode.OK);
    }

    public static Response getClientDetails(Long clientId, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.GET, EndPoint.GET_CLIENT_PATH, clientId);
    }

    public static Response updateClientDetails(ClientVO clientUpdateVO) {
        return updateClientDetails(clientUpdateVO, StatusCode.OK);
    }

    public static Response updateClientDetails(ClientVO clientUpdateVO, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(clientUpdateVO)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.UPDATE_CLIENT_PATH);
    }

    public static Response addClientFieldMappings(Long clientId, List<ClientFieldMappingVO> clientFieldMappingVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(clientFieldMappingVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.ADD_CLIENT_FIELD_MAPPING_PATH, clientId);
    }

    public static Response getClientFieldMappings(Long clientId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_CLIENT_FIELD_MAPPING_PATH, clientId);
    }

    /**
     * Save client theme
     *
     * @param clientThemeVO The {@link ClientThemeVO} containing request body
     * @return {@link Response} object containing response body as {@code ResponseWO<String>}
     */
    public static Response saveClientTheme(ClientThemeVO clientThemeVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(clientThemeVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.SAVE_CLIENT_THEME_PATH);
    }

    /**
     * Get client theme
     *
     * @param clientId The client id
     * @return {@link Response} object containing response body as {@code ResponseWO<ClientThemeVO>}
     */
    public static Response getClientTheme(Long clientId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_CLIENT_THEME_PATH, clientId);
    }


}
