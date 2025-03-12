package in.credable.automation.service.integration;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.integration.CreditCheckVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class TUCIBILService {
    private TUCIBILService() {
    }

    public static Response creditCheck(String id, CreditCheckVO creditCheckVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .body(creditCheckVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.CREDIT_CHECK_PATH, id);
    }
}
