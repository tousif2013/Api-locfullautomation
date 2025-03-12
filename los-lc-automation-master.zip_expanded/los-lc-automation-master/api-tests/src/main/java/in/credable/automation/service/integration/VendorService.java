package in.credable.automation.service.integration;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.integration.ListVendorsRequestVO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class VendorService {
    private VendorService() {
    }

    public static Response addVendor(VendorVO vendorVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .contentType(ContentType.JSON)
                .body(vendorVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.ADD_VENDOR_PATH);

    }

    public static Response listAllVendors(ListVendorsRequestVO listVendorsRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .contentType(ContentType.JSON)
                .body(listVendorsRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.LIST_ALL_VENDORS_PATH);
    }

    public static Response updateVendor(VendorVO vendorVO, String vendorId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .contentType(ContentType.JSON)
                .body(vendorVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.UPDATE_VENDOR_PATH, vendorId);
    }
}
