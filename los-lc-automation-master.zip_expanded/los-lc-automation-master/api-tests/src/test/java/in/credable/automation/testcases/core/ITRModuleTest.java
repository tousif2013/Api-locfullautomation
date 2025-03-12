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

public class ITRModuleTest extends BaseTest {
    private static final Long AUTOMATION_PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();
    private String vendorId;
    private LoanApplicationVO loanApplicationVO;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor("ITR", "scoreMeService");
        vendorId = vendor.getId();
    }

    @BeforeClass(dependsOnMethods = "createVendor")
    public void createLoanApplication() {
        loanApplicationVO = TestHelper.createLoanApplication(AUTOMATION_PROGRAM_ID);
    }

    @Test(description = "Test #882 - Verify that the ITR Module works correctly when hit from postman")
    public void verifyITRModule() {
        Map<String, Object> variables = new HashMap<>(2);
        variables.put("itrUsername", RandomDataGenerator.generateRandomString(10));
        variables.put("itrPassword", RandomDataGenerator.generateRandomString(10));

        Map<String, Object> itrIntegrationConfigData = new HashMap<>(3);
        itrIntegrationConfigData.put("itrType", "ITR-3");
        itrIntegrationConfigData.put("integrationType", "ITR");
        itrIntegrationConfigData.put("vendorCode", vendorId);

        RulesIntegrationVO rulesIntegrationVO = new RulesIntegrationVO();
        rulesIntegrationVO.setIntegrationType("RULES");
        RuleVO ruleVO = new RuleVO();
        ruleVO.setId("5452da33-1cab-b321-83aa-e44ef4f2773f");
        ruleVO.setVersion("1");
        ruleVO.setUrl("");
        ruleVO.setAsync(Boolean.FALSE);
        rulesIntegrationVO.setRules(List.of(ruleVO));

        Map<String, Object> ruleIntegrationMap = SerializationUtil.convertObjectToMap(rulesIntegrationVO);

        ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO = ModuleExecutionInfoRequestVO.builder()
                .moduleCode("incomeTaxReport")
                .moduleInstanceName("incomeTaxReport")
                .moduleInstanceID("incomeTaxReport")
                .clientId(1)
                .userId("1")
                .programId(AUTOMATION_PROGRAM_ID.intValue())
                .variables(variables)
                .moduleConfigData(List.of(itrIntegrationConfigData, ruleIntegrationMap))
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
                .moduleExecutionStatusIs(ModuleExecutionStatusEnum.SYNCPART_COMPLETED)
                .executionResultIsNotEmpty();
    }

    @AfterClass(alwaysRun = true)
    public void changeVendorStatusToInactive() {
        if (StringUtils.isNotBlank(vendorId)) {
            TestHelper.changeVendorStatusToInactive(vendorId);
        }
    }
}
