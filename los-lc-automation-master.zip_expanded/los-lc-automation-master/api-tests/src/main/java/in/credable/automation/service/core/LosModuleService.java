package in.credable.automation.service.core;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderName;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.core.ModuleExecutionInfoRequestVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.Map;

/**
 * @author Prashant Rana
 */
public final class LosModuleService {
    private LosModuleService() {
    }

    /**
     * Executes specified module with the given transactionId
     *
     * @param moduleExecutionInfoRequestVO The ModuleExecutionInfoRequestVO object
     * @param transactionId                The transactionId of the request
     * @return The {@link Response} object containing body as {@code ResponseWO<ModuleExecutionInfoResponseVO>}
     */
    public static Response executeLosModule(ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO, String transactionId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .header(HeaderName.TRANSACTION_ID, transactionId)
                .headers(HeaderProvider.getLosCoreHeaders())
                .contentType(ContentType.JSON)
                .body(moduleExecutionInfoRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.EXECUTE_LOS_MODULE_PATH);
    }

    /**
     * @param queryParams Following query parameters are required:
     *                    <ul>
     *                    <li>loanApplicationId</li>
     *                    <li>moduleCode</li>
     *                    <li>moduleInstanceId</li>
     *                    <li>transactionId</li>
     *                    </ul>
     * @return The {@link Response} object containing body as {@code ResponseWO<ModuleStatusVO>}
     */
    public static Response fetchModuleStatus(Map<String, Object> queryParams) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getLosCoreHeaders())
                .queryParams(queryParams)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.FETCH_MODULE_STATUS_PATH);
    }

}
