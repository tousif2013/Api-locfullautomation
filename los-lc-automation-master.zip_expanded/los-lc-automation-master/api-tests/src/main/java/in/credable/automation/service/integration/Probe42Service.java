package in.credable.automation.service.integration;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class Probe42Service {

    private Probe42Service() {
    }

    public static Response getLLPComprehensiveDetail(String id, String paramName, String paramValue) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam(paramName, paramValue)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.MCA_LLP_COMPREHENSIVEDETAILS_PATH, id);
    }

    public static Response getLLPBasicDetail(String id, String paramName, String paramValue) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam(paramName, paramValue)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.MCA_LLP_BASICDETAILS_PATH, id);
    }

    public static Response getCompanyComprehensiveDetail(String id, String paramName, String paramValue) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam(paramName, paramValue)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.MCA_COMPANY_COMPREHENSIVEDETAILS_PATH, id);
    }

    public static Response getCompanyBasicDetail(String id, String paramName, String paramValue) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam(paramName, paramValue)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.MCA_COMPANY_BASICDETAILS_PATH, id);
    }

}
