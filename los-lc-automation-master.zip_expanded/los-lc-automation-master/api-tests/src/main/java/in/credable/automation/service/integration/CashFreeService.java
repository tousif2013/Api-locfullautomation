package in.credable.automation.service.integration;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.integration.CashFreeVO;
import in.credable.automation.utils.EndPoint;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

/**
 * @author PriyankaSingh on 21/03/2024.
 */
public class CashFreeService {

    private CashFreeService() {
    }

    public static Response BankAccountVerificationApiRequest(CashFreeVO cashFreeRequestVo, String vendorId) {

        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.INTEGRATION_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .headers(HeaderProvider.getIntegrationServiceHeaders())
                .body(cashFreeRequestVo)
                .accept(ContentType.JSON)
                .request(Method.POST, EndPoint.CASHFREE_BANKACCOUNT_VERIFICATION, vendorId);

    }
}
