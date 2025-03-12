package in.credable.automation.testcases.core;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.commons.utils.SerializationUtil;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.core.LosModuleService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.core.*;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.RandomDataGenerator;
import io.restassured.common.mapper.TypeRef;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditBureauCheckModuleTest extends BaseTest {
    private static final Long AUTOMATION_PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();
    private String vendorId;
    private LoanApplicationVO loanApplicationVO;
    private Map<String, Object> executionResult;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor("CREDIT-CHECK", "tuCibilService");
        vendorId = vendor.getId();
    }

    @BeforeClass(dependsOnMethods = "createVendor")
    public void createLoanApplication() {
        loanApplicationVO = TestHelper.createLoanApplication(AUTOMATION_PROGRAM_ID);
    }

    @Test(description = "Test #885 - Verify that the Credit Bureau Check Module can fetch credit bureau details and identify what category profile falls into.")
    public void verifyCreditBureauCheckModule() {
        Map<String, Object> variables = getVariablesForCreditBureauCheckModule();

        ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO = ModuleExecutionInfoRequestVO.builder()
                .moduleCode("creditBureauCheck")
                .moduleInstanceName("creditBureauCheck")
                .moduleInstanceID("creditBureauCheck")
                .clientId(1)
                .userId("1")
                .programId(AUTOMATION_PROGRAM_ID.intValue())
                .variables(variables)
                .moduleConfigData(List.of(getCreditBureauConfigData(), getRulesIntegrationConfigData()))
                .loanApplicationId(loanApplicationVO.getId())
                .build();

        ResponseWO<ModuleExecutionInfoResponseVO> responseWO =
                LosModuleService.executeLosModule(moduleExecutionInfoRequestVO, RandomDataGenerator.getUuid())
                        .as(new TypeRef<>() {
                        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("module.executed")
                .hasSameMessage("Module executed successfully")
                .timestampIsNotNull()
                .dataIsNotNull();
        ModuleExecutionInfoResponseVO moduleExecutionInfoResponseVO = responseWO.getData();
        FrameworkAssertions.assertThat(moduleExecutionInfoResponseVO)
                .loanApplicationIdIs(loanApplicationVO.getId())
                .moduleCodeIs(moduleExecutionInfoRequestVO.getModuleCode())
                .moduleInstanceIdIs(moduleExecutionInfoRequestVO.getModuleInstanceID())
                .moduleExecutionStatusIs(ModuleExecutionStatusEnum.COMPLETED)
                .executionResultIsNotEmpty();

        executionResult = moduleExecutionInfoResponseVO.getExecutionResult();
    }

    @Test(description = "Test #864 - Verify that the Execute Offer Module is executed correctly when we hit API"
            , dependsOnMethods = "verifyCreditBureauCheckModule")
    public void verifyExecuteOfferModule() {
        ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO = ModuleExecutionInfoRequestVO.builder()
                .moduleCode("bureauBasedOffer")
                .moduleInstanceName("bureauBasedOffer")
                .moduleInstanceID("bureauBasedOffer")
                .clientId(1)
                .userId("1")
                .programId(AUTOMATION_PROGRAM_ID.intValue())
                .variables(executionResult)
                .moduleConfigData(List.of(getCreditBureauConfigData(), getRulesIntegrationConfigData()))
                .loanApplicationId(loanApplicationVO.getId())
                .build();

        ResponseWO<ModuleExecutionInfoResponseVO> responseWO =
                LosModuleService.executeLosModule(moduleExecutionInfoRequestVO, RandomDataGenerator.getUuid())
                        .as(new TypeRef<>() {
                        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("module.executed")
                .hasSameMessage("Module executed successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        ModuleExecutionInfoResponseVO moduleExecutionInfoResponseVO = responseWO.getData();
        FrameworkAssertions.assertThat(moduleExecutionInfoResponseVO)
                .loanApplicationIdIs(loanApplicationVO.getId())
                .moduleCodeIs(moduleExecutionInfoRequestVO.getModuleCode())
                .moduleInstanceIdIs(moduleExecutionInfoRequestVO.getModuleInstanceID())
                .moduleExecutionStatusIs(ModuleExecutionStatusEnum.COMPLETED)
                .executionResultIsNotEmpty();
    }

    @AfterClass(alwaysRun = true)
    public void changeVendorStatusToInactive() {
        if (StringUtils.isNotBlank(vendorId)) {
            TestHelper.changeVendorStatusToInactive(vendorId);
        }
    }

    private Map<String, Object> getVariablesForCreditBureauCheckModule() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", RandomDataGenerator.generateRandomFirstName());
        variables.put("phone", RandomDataGenerator.generateRandomMobileNumber());
        variables.put("email", RandomDataGenerator.generateRandomUniqueEmailId());
        variables.put("residenceAddress", Map.of("addressLine1", "address line 1"
                , "pincode", "123456"
                , "state", "Maharashtra"));
        variables.put("dateOfBirth", "2000-01-01");
        variables.put("pan", RandomDataGenerator.generateRandomPanNumber());
        variables.put("gender", "MALE");
        return variables;
    }

    private Map<String, Object> getCreditBureauConfigData() {
        Map<String, Object> creditBureauConfigData = new HashMap<>();
        creditBureauConfigData.put("integrationType", "CREDIT_BUREAU");
        creditBureauConfigData.put("vendorCode", vendorId);
        creditBureauConfigData.put("enquiryAmount", "8000000");
        creditBureauConfigData.put("enquiryPurpose", "test eligibility");
        return creditBureauConfigData;
    }

    private Map<String, Object> getRulesIntegrationConfigData() {
        RulesIntegrationVO rulesIntegrationVO = new RulesIntegrationVO();
        rulesIntegrationVO.setIntegrationType("RULES");
        RuleVO ruleVO = new RuleVO();
        ruleVO.setId("4cb97d7a-dfa7-d687-fb86-5cdb83df8593");
        ruleVO.setVersion("1");
        ruleVO.setUrl("");
        ruleVO.setAsync(Boolean.FALSE);
        rulesIntegrationVO.setRules(List.of(ruleVO));
        return SerializationUtil.convertObjectToMap(rulesIntegrationVO);
    }
}
