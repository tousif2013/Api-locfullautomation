package in.credable.automation.service.client;

import in.credable.automation.config.ConfigFactory;
import in.credable.automation.config.EnvironmentConfig;
import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderName;
import in.credable.automation.service.vo.client.AnchorSubSegmentVO;
import in.credable.automation.service.vo.client.AnchorVO;
import in.credable.automation.service.vo.client.FilterAnchorVO;
import in.credable.automation.service.vo.client.PANVerificationVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.Map;

public final class AnchorService {
    private static final String ORG_ID_HEADER_NAME = HeaderName.ORG_ID;

    private static final String ORG_ID_HEADER_VALUE;

    static {
        EnvironmentConfig environmentConfig = ConfigFactory.getEnvironmentConfig();
        ORG_ID_HEADER_VALUE = environmentConfig.getKeyCloakOrganizationId();

    }

    private AnchorService() {
    }

    public static Response createAnchor(Long clientId, AnchorVO anchorRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(anchorRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.CREATE_ANCHOR_PATH, clientId);
    }

    public static Response getAnchorsForClient(Long clientId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_ANCHORS_FOR_CLIENT_PATH, clientId);
    }

    public static Response addAnchor(AnchorVO anchorRequestVO, StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .contentType(ContentType.JSON)
                .body(anchorRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.ADD_ANCHOR_PATH);
    }

    public static Response addAnchor(AnchorVO anchorRequestVO) {
        return addAnchor(anchorRequestVO, StatusCode.OK);
    }

    public static Response updateAnchor(AnchorVO anchorRequestVO, StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .contentType(ContentType.JSON)
                .body(anchorRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.UPDATE_ANCHOR_PATH);
    }

    public static Response updateAnchor(AnchorVO anchorRequestVO) {
        return updateAnchor(anchorRequestVO, StatusCode.OK);
    }

    public static Response approveAnchor(Long id, StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.APPROVE_ANCHOR_PATH, id);
    }

    public static Response approveAnchor(Long id) {
        return approveAnchor(id, StatusCode.OK);
    }

    public static Response rejectAnchor(Long id) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.REJECT_ANCHOR_PATH, id);
    }

    public static Response verifyBusinessPAN(PANVerificationVO panVerificationVO, StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .contentType(ContentType.JSON)
                .body(panVerificationVO)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.VERIFY_BUSINESSPAN_PATH);
    }

    public static Response fetchGSTINList(PANVerificationVO panVerificationVO, StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .contentType(ContentType.JSON)
                .body(panVerificationVO)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.FETCH_GSTIN_LIST_PATH);
    }

    public static Response addSubsegment(Long id, AnchorSubSegmentVO anchorSubSegmentVO, StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .contentType(ContentType.JSON)
                .body(anchorSubSegmentVO)
                .accept(ContentType.TEXT)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.ADD_SUBSEGMENT_PATH, id);
    }

    public static Response fetchAnchorDetail(Long id) {
        return fetchAnchorDetail(id, StatusCode.OK);
    }

    public static Response fetchAnchorDetail(Long id, StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.GET, EndPoint.FETCH_ANCHOR_DETAIL_PATH, id);
    }

    public static Response getAnchorSubsegment(Long id, StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.GET, EndPoint.GET_ANCHOR_SUBSEGMENT_PATH, id);
    }

    public static Response getAnchorSubsegment(Long id) {
        return getAnchorSubsegment(id, StatusCode.OK);
    }

    public static Response getAnchorList(StatusCode statusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.GET, EndPoint.GET_ANCHOR_LIST_PATH);
    }

    public static Response filterAnchor(FilterAnchorVO filterAnchorVO, Map<String, Object> queryParam) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .header(ORG_ID_HEADER_NAME, ORG_ID_HEADER_VALUE)
                .queryParams(queryParam)
                .contentType(ContentType.JSON)
                .body(filterAnchorVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.FILTER_ANCHOR_PATH);
    }

    /**
     * Get all anchor asociated with client
     *
     * @param clientId The client id
     * @return {@link Response} object containing response body as {@code List<AnchorVO>}
     */
    public static Response getAllAnchors(Long clientId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_ALL_ANCHORS_PATH, clientId);
    }
}
