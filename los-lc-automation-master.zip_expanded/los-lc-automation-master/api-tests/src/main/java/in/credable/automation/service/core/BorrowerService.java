package in.credable.automation.service.core;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.core.FetchBorrowerRequestVO;
import in.credable.automation.service.vo.core.ListBorrowersRequestVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class BorrowerService {
    private BorrowerService() {
    }

    public static Response listBorrowers(ListBorrowersRequestVO listBorrowersRequestVO) {
        return RestAssuredClient
                .withDefaultBaseUrl()
                .enableLoggingFilters(true, false)
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getLosCoreHeaders())
                .contentType(ContentType.JSON)
                .body(listBorrowersRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.LIST_BORROWERS_PATH);
    }

    public static Response fetchBorrower(String borrowerId, FetchBorrowerRequestVO fetchBorrowerRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getLosCoreHeaders())
                .contentType(ContentType.JSON)
                .body(fetchBorrowerRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.FETCH_BORROWER_PATH, borrowerId);
    }
}
