package in.credable.automation.service.integration;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.integration.IdifyEKYCDownloadRequestVO;
import in.credable.automation.service.vo.integration.IdifyEKYCSearchRequestVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class CKYCService {
    private CKYCService() {
    }


    /**
     * @param vendorId                 The CKYC service vendor id
     * @param idifyEKYCSearchRequestVO The request body
     * @return The {@link Response} object containing response body as {@code ResponseWO<Object>}
     */
    public static Response ckycSearch(String vendorId, IdifyEKYCSearchRequestVO idifyEKYCSearchRequestVO) {
        return ckycSearch(vendorId, idifyEKYCSearchRequestVO, StatusCode.OK);
    }

    /**
     * @param vendorId                 The CKYC service vendor id
     * @param idifyEKYCSearchRequestVO The request body
     * @param expectedStatusCode       The expected response code
     * @return The {@link Response} object containing response body as {@code ResponseWO<Object>}
     */
    public static Response ckycSearch(String vendorId, IdifyEKYCSearchRequestVO idifyEKYCSearchRequestVO,
                                      StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .contentType(ContentType.JSON)
                .body(idifyEKYCSearchRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.CKYC_SEARCH_PATH, vendorId);
    }

    /**
     * @param vendorId                   The CKYC service vendor id
     * @param idifyEKYCDownloadRequestVO The request body
     * @return The {@link Response} object containing response body as {@code ResponseWO<Object>}
     */
    public static Response ckycDownload(String vendorId, IdifyEKYCDownloadRequestVO idifyEKYCDownloadRequestVO) {
        return ckycDownload(vendorId, idifyEKYCDownloadRequestVO, StatusCode.OK);
    }

    /**
     * @param vendorId                   The CKYC service vendor id
     * @param idifyEKYCDownloadRequestVO The request body
     * @param expectedStatusCode         The expected response code
     * @return The {@link Response} object containing response body as {@code ResponseWO<Object>}
     */
    public static Response ckycDownload(String vendorId, IdifyEKYCDownloadRequestVO idifyEKYCDownloadRequestVO,
                                        StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .contentType(ContentType.JSON)
                .body(idifyEKYCDownloadRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.CKYC_DOWNLOAD_PATH, vendorId);
    }

}
