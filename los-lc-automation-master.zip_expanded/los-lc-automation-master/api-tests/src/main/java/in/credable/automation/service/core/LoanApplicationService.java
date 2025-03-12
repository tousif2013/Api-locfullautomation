package in.credable.automation.service.core;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.core.LoanCreationRequestVO;
import in.credable.automation.service.vo.core.LoanUpdationRequestVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class LoanApplicationService {
    private LoanApplicationService() {
    }

    public static Response createLoanApplication(LoanCreationRequestVO loanCreationRequestVO) {
        return createLoanApplication(loanCreationRequestVO, StatusCode.OK);
    }

    public static Response createLoanApplication(LoanCreationRequestVO loanCreationRequestVO, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getLosCoreHeaders())
                .contentType(ContentType.JSON)
                .body(loanCreationRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.CREATE_LOAN_APPLICATION_PATH);
    }

    public static Response getAllLoanApplicationsForBorrower(String borrowerId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getLosCoreHeaders())
                .queryParam("borrowerId", borrowerId)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_LOAN_APPLICATIONS_FOR_BORROWER_PATH);
    }

    public static Response getLoanApplicationDetailsById(String loanApplicationId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getLosCoreHeaders())
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_LOAN_APPLICATION_BY_ID_PATH, loanApplicationId);
    }

    public static Response updateLoanApplication(String loanApplicationId, LoanUpdationRequestVO loanUpdationRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getLosCoreHeaders())
                .contentType(ContentType.JSON)
                .body(loanUpdationRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.PUT, EndPoint.UPDATE_LOAN_APPLICATION_PATH, loanApplicationId);
    }
}
