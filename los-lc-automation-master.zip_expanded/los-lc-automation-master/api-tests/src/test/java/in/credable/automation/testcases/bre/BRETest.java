package in.credable.automation.testcases.bre;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.assertions.JsonPathAssertion;
import in.credable.automation.service.bre.BREService;
import in.credable.automation.service.vo.bre.*;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.RandomDataGenerator;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class BRETest extends BaseTest {

    private static final String ANCHOR_RECOMENDED_RULE_ID = "4cb97d7a-dfa7-d687-fb86-5cdb83df8593";
    private final String ANCHOR_RECOMENDED_RULE_VERSION = "1";
    private String lower_threshold;
    private String upper_threshold;

    @Test(description = "TC#289-Verify if List all rule  API is working correctly.")
    public void verifylistAllRules() {
        BreVO<List<RuleDetailVO>> responseVO = BREService.listAllRules().as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .responseCodeisNotNull()
                .hasSameMessage(ApiMessageConstants.LIST_ALL_RULES_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotEmptyList()
                .dataIsNotNull()
                .dataIsNotEmptyList();
    }

    @Test(description = "TC#286-Verify if Update rule  API is working correctly.")
    public void verifyupdateRuleVariable() {
        UpdateRuleVO updateRuleVO = new UpdateRuleVO();
        lower_threshold = RandomDataGenerator.generateRandomNumber(6).toString();
        upper_threshold = RandomDataGenerator.generateRandomNumber(7).toString();
        updateRuleVO.setLowerThreshold(lower_threshold);
        updateRuleVO.setUpperThreshold(upper_threshold);
        BreVO<Object> responseVO = BREService.updateRuleVariable(ANCHOR_RECOMENDED_RULE_ID, ANCHOR_RECOMENDED_RULE_VERSION, updateRuleVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseVO)
                .responseCodeisNotNull()
                .hasSameMessage(ApiMessageConstants.UPDATE_RULE_INFO_MESSAGE)
                .timeStampIsNotNull();

    }

    @Test(description = "TC#283-Verify if Get rule Info API is working correctly.", dependsOnMethods = "verifyupdateRuleVariable")
    public void VerifyGetRuleInfo() {
        Response response = BREService.getRuleInfo(ANCHOR_RECOMENDED_RULE_ID, ANCHOR_RECOMENDED_RULE_VERSION);
        BreVO<Object> responseVO = response.as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseVO)
                .responseCodeisNotNull()
                .hasSameMessage(ApiMessageConstants.FETCH_RULE_INFO_MESSAGE)
                .timeStampIsNotNull()
                .dataIsNotNull();

        JsonPathAssertion.assertThat(response).assertString("data[0].variables.lower_threshold", lower_threshold)
                .assertString("data[0].variables.upper_threshold", upper_threshold);
    }

    @Test(description = "TC#292-Verify if Execute Business rule API is working correctly.", dependsOnMethods = "verifyupdateRuleVariable")
    public void VerifyExecutebusinessRule() {
        ExecuteBusinessRuleVO executeBusinessRuleVO = DataProviderUtil.manufacturePojo(ExecuteBusinessRuleVO.class);
        int recomendedlimit = (Integer.parseInt(lower_threshold) + Integer.parseInt(upper_threshold)) / 2;

        BusinessRuleVO businessRule = new BusinessRuleVO();
        businessRule.setId(ANCHOR_RECOMENDED_RULE_ID);

        businessRule.setVersion(ANCHOR_RECOMENDED_RULE_VERSION);
        executeBusinessRuleVO.setBusinessRules(List.of(businessRule));

        InputDataVO inputData = new InputDataVO();
        inputData.setRecommendedLimit(recomendedlimit);
        executeBusinessRuleVO.setInputData(inputData);

        Response response = BREService.executeBusinessRule(executeBusinessRuleVO);
        BreVO<Object> responseVO = response.as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .responseCodeisNotNull()
                .hasSameMessage(ApiMessageConstants.BUSINESS_RULE_EXECUTED_MESSAGE)
                .timeStampIsNotNull();

        JsonPathAssertion.assertThat(response).assertString("outputData[0].br_id", ANCHOR_RECOMENDED_RULE_ID)
                .assertString("outputData[0].input.recommendedLimit", String.valueOf(recomendedlimit))
                .assertString("outputData[0].output.status", "PASS")
                .assertString("outputData[0].output.message", ApiMessageConstants.ANCHOR_RECOMMENDED_LIMIT_PASS_MESSAGE)
                .assertString("outputData[0].output.actual_value", String.valueOf(recomendedlimit))
                .assertString("outputData[0].status", "SUCCESS");
    }

}
