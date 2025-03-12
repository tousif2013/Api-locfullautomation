package in.credable.automation.service.integration;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.integration.DigioVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

public final class DigioService {
    private DigioService() {
    }

    public static Response submitEsignRequest(DigioVO DigoRequestVo, String vendorId) {

        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .body(DigoRequestVo)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.ESIGN_SUBMIT_REQUEST, vendorId);

    }

    public static Response eSignDocumentDetail(String sourceReferenceId, String vendorId) {

        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", sourceReferenceId)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.ESIGN_DETAILS_REQUEST, vendorId);

    }

    public static Response cancelEsignRequest(String sourceReferenceId, String vendorId) {
        HashMap<String, Object> map = new HashMap<>();
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .body(map)
                .queryParam("sourceReferenceId", sourceReferenceId)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.DELETE, EndPoint.ESIGN_CANCEL_REQUEST, vendorId);

    }

    public static Response cancelEsignRequest(String sourceReferenceId, String vendorId, String SignURL) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("signUrl", SignURL);
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", sourceReferenceId)
                .body(map)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.DELETE, EndPoint.ESIGN_CANCEL_REQUEST, vendorId);

    }

    public static Response downloadEsignDocument(String sourceReferenceId, String vendorId) {
        return downloadEsignDocument(sourceReferenceId, vendorId, StatusCode.OK);
    }

    public static Response downloadEsignDocument(String sourceReferenceId, String vendorId, StatusCode statusCode) {

        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", sourceReferenceId)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.GET, EndPoint.ESIGN_DOWNLOADS_REQUEST, vendorId);
    }

    public static Response resendEsignNotification(String sourceReferenceId, String vendorId, List<String> urlList) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("signUrls", urlList);

        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", sourceReferenceId)
                .body(map)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.ESIGN_RESEND_NOTIFICATION_PATH, vendorId);
    }

    public static Response eSignTransactionStatus(String sourceReferenceId, String vendorId) {

        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", sourceReferenceId)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.ESIGN_TRANSACTION_STATUS_PATH, vendorId);
    }

    public static Response deleteEsignInvitation(String sourceReferenceId, String vendorId) {
        return deleteEsignInvitation(sourceReferenceId, vendorId, StatusCode.OK);
    }

    public static Response deleteEsignInvitation(String sourceReferenceId, String vendorId, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", sourceReferenceId)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.DELETE, EndPoint.ESIGN_DELETE_INVITATION_PATH, vendorId);
    }
}
