package in.credable.automation.service.integration;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.CrimeReportCompanyRequestVO;
import in.credable.automation.service.vo.integration.CrimeReportIndividualRequestVO;
import in.credable.automation.service.vo.integration.WatchOutRequestVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.Map;

/**
 * @author Prashant Rana
 */
public final class FraudCheckService {
    private FraudCheckService() {
    }

    public static Response watchOutCheck(String vendorId, WatchOutRequestVO watchOutRequestVO) {
        return watchOutCheck(vendorId, watchOutRequestVO, StatusCode.OK, HeaderProvider.getIntegrationServiceHeaders());
    }

    /**
     * @param vendorId           The vendor id
     * @param watchOutRequestVO  The request body
     * @param expectedStatusCode Expected status code
     * @return The {@link Response} object containing response body as {@code ResponseWO<CrimeCheckResponseVO>}
     */
    public static Response watchOutCheck(String vendorId,
                                         WatchOutRequestVO watchOutRequestVO,
                                         StatusCode expectedStatusCode) {
        return watchOutCheck(vendorId, watchOutRequestVO, expectedStatusCode, HeaderProvider.getIntegrationServiceHeaders());
    }

    /**
     * @param vendorId           The vendor id
     * @param watchOutRequestVO  The request body
     * @param expectedStatusCode Expected status code
     * @param headers            Headers containing client_id, source_id
     * @return The {@link Response} object containing response body as {@code ResponseWO<CrimeCheckResponseVO>}
     */
    public static Response watchOutCheck(String vendorId,
                                         WatchOutRequestVO watchOutRequestVO,
                                         StatusCode expectedStatusCode,
                                         Map<String, String> headers) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(watchOutRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.WATCH_OUT_CHECK_PATH, vendorId);
    }

    /**
     * @param vendorId                    The vendor id for crimeCheck service
     * @param crimeReportCompanyRequestVO The request body
     * @return The {@link Response} object containing response body as {@code ResponseWO<CrimeCheckResponseVO>}
     */
    public static Response submitCrimeCheckRequestForCompany(String vendorId, CrimeReportCompanyRequestVO crimeReportCompanyRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .contentType(ContentType.JSON)
                .body(crimeReportCompanyRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.CRIME_CHECK_FOR_COMPANY_PATH, vendorId);
    }

    /**
     * @param vendorId                       The vendor id for crimeCheck service
     * @param crimeReportIndividualRequestVO The request body
     * @return The {@link Response} object containing response body as {@link ResponseWO}
     */
    public static Response submitCrimeCheckRequestForIndividual(String vendorId, CrimeReportIndividualRequestVO crimeReportIndividualRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .contentType(ContentType.JSON)
                .body(crimeReportIndividualRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.CRIME_CHECK_FOR_INDIVIDUAL_PATH, vendorId);
    }


    /**
     * @param vendorId          The vendor id for crimeCheck service
     * @param sourceReferenceId The source reference id used in creating report for crime-check
     * @return The {@link Response} object containing response body as {@code ResponseWO<CrimeCheckJsonReportResponseVO>}
     */
    public static Response getCrimeCheckJsonReport(String vendorId, String sourceReferenceId) {
        return getCrimeCheckJsonReport(vendorId, sourceReferenceId, StatusCode.OK);
    }

    /**
     * @param vendorId           The vendor id for crimeCheck service
     * @param sourceReferenceId  The source reference id used in creating report for crime-check
     * @param expectedStatusCode Expected status code
     * @return The {@link Response} object containing response body as {@code ResponseWO<CrimeCheckJsonReportResponseVO>}
     */
    public static Response getCrimeCheckJsonReport(String vendorId, String sourceReferenceId, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", sourceReferenceId)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.GET, EndPoint.GET_CRIME_CHECK_JSON_REPORT_PATH, vendorId);
    }


    /**
     * @param vendorId          The vendor id for crimeCheck service
     * @param sourceReferenceId The source reference id used in creating report for crime-check
     * @return The {@link Response} object containing response body as {@code ResponseWO<CrimeCheckReportVO>}
     */
    public static Response getCrimeCheckPdfReport(String vendorId, String sourceReferenceId) {
        return getCrimeCheckPdfReport(vendorId, sourceReferenceId, StatusCode.OK);
    }

    /**
     * @param vendorId           The vendor id for crimeCheck service
     * @param sourceReferenceId  The source reference id used in creating report for crime-check
     * @param expectedStatusCode Expected status code
     * @return The {@link Response} object containing response body as {@code ResponseWO<CrimeCheckReportVO>}
     */
    public static Response getCrimeCheckPdfReport(String vendorId, String sourceReferenceId, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", sourceReferenceId)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.GET, EndPoint.GET_CRIME_CHECK_PDF_REPORT_PATH, vendorId);
    }
}
