package in.credable.automation.service.bre;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.bre.ExecuteBusinessRuleVO;
import in.credable.automation.service.vo.bre.UpdateRuleVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class BREService {

    private BREService() {
    }

    /**
     * List all the BRE rule from decision rule.
     *
     * @return {@link Response} object containing response body as {@code BreVO<List<RuleDetailVO>>}
     */
    public static Response listAllRules() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.BUSINESS_RULES_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.LIST_ALL_RULES_PATH);
    }


    /**
     * get rule info of a specific rule by rule id
     *
     * @param ruleId
     * @param ruleVersion
     * @return {@link Response} object containing response body as {@code BreVO<Object>}
     */
    public static Response getRuleInfo(String ruleId, String ruleVersion) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.BUSINESS_RULES_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.RULE_DETAIL_PATH, ruleId, ruleVersion);
    }

    /**
     * get rule info of a specific rule by rule id
     *
     * @param ruleId
     * @param ruleVersion
     * @param updateRuleVO {@link UpdateRuleVO} object
     * @return {@link Response} object containing response body as {@code BreVO<Object>}
     */
    public static Response updateRuleVariable(String ruleId, String ruleVersion, UpdateRuleVO updateRuleVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.BUSINESS_RULES_SERVICE_BASE_PATH)
                .body(updateRuleVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.PUT, EndPoint.RULE_DETAIL_PATH, ruleId, ruleVersion);
    }

    /**
     * get rule info of a specific rule by rule id
     *
     * @param executeBusinessRuleVO {@link ExecuteBusinessRuleVO} object
     * @return {@link Response} object containing response body as {@code BreVO<Object>}
     */
    public static Response executeBusinessRule(ExecuteBusinessRuleVO executeBusinessRuleVO) {

        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.BUSINESS_RULES_SERVICE_BASE_PATH)
                .body(executeBusinessRuleVO)
                .headers(HeaderProvider.getExecuteBusinessRuleHeaders())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.EXECUTE_RULE_PATH);
    }
}