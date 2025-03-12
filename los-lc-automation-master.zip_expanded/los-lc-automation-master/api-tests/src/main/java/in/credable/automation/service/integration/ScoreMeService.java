package in.credable.automation.service.integration;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.integration.SubmitITRRequestVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class ScoreMeService {
    private ScoreMeService() {
    }

    public static Response itrSubmitRequest(String id, StatusCode statusCode, SubmitITRRequestVO submitITRRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .body(submitITRRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.POST, EndPoint.ITR_SUBMIT_REQUEST_PATH, id);
    }

    public static Response itrReport(String id, StatusCode statusCode, String queryParam) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", queryParam)
                .accept(ContentType.JSON)
                .expectStatusCode(statusCode.getStatusCode())
                .request(Method.GET, EndPoint.ITR_REPORT_PATH, id);
    }
}
