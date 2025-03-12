package in.credable.automation.service.integration;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.integration.GstCreateOrderRequestVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class BusinessDetailService {
    private BusinessDetailService() {
    }

    /**
     * Verifies PAN
     *
     * @param vendorId The vendor id
     * @param pan      PAN number
     * @return {@link Response} object containing response body as {@code ResponseWO<Object>}
     */
    public static Response verifyPan(String vendorId, String pan) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("pan", pan)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.VERIFY_PAN_PATH, vendorId);
    }

    /**
     * Fetches GSTIN for the specified PAN
     *
     * @param vendorId The vendor id
     * @param pan      PAN number
     * @return {@link Response} object containing response body as {@code ResponseWO<Object>}
     */
    public static Response listGstin(String vendorId, String pan) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("pan", pan)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.LIST_GSTIN_PATH, vendorId);
    }

    /**
     * Fetches GSTIN details for the specified GSTIN number
     *
     * @param vendorId The vendor id
     * @param gst      The GSTIN number
     * @return {@link Response} object containing response body as {@code ResponseWO<Object>}
     */
    public static Response getGstinDetails(String vendorId, String gst) {
        return getGstinDetails(vendorId, gst, StatusCode.OK);
    }

    /**
     * Fetches GSTIN details for the specified GSTIN number
     *
     * @param vendorId           The vendor id
     * @param gst                The GSTIN number
     * @param expectedStatusCode The expected status code
     * @return {@link Response} object containing response body as {@code ResponseWO<Object>}
     */
    public static Response getGstinDetails(String vendorId, String gst, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("gstin", gst)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.GET, EndPoint.GET_GSTIN_DETAILS_PATH, vendorId);
    }

    /**
     * Creates GST order
     *
     * @param vendorId                The IRIS vendor id
     * @param gstCreateOrderRequestVO The request body
     * @param expectedStatusCode      The expected status code
     * @return The {@link Response} object containing response body as {@code ResponseWO<Void>}
     */
    public static Response createGstOrder(String vendorId, GstCreateOrderRequestVO gstCreateOrderRequestVO, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .contentType(ContentType.JSON)
                .body(gstCreateOrderRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.CREATE_GST_ORDER_PATH, vendorId);
    }

    /**
     * Confirms GST order
     *
     * @param vendorId           The IRIS vendor id
     * @param sourceReferenceId  The source reference id
     * @param expectedStatusCode The expected status code
     * @return The {@link Response} object containing response body as {@code ResponseWO<Void>}
     */
    public static Response confirmGstOrder(String vendorId, String sourceReferenceId, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", sourceReferenceId)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.PUT, EndPoint.CONFIRM_GST_ORDER_PATH, vendorId);
    }

    /**
     * Fetches GST report
     *
     * @param vendorId           The IRIS vendor id
     * @param sourceReferenceId  The source reference id
     * @param expectedStatusCode The expected status code
     * @return The {@link Response} object containing response body as {@code ResponseWO<Void>}
     */
    public static Response getGstReport(String vendorId, String sourceReferenceId, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .queryParam("sourceReferenceId", sourceReferenceId)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.GET, EndPoint.GET_GST_REPORT_PATH, vendorId);
    }

}
