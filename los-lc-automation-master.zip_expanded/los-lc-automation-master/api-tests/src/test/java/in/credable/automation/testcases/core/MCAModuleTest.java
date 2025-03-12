package in.credable.automation.testcases.core;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.core.LosModuleService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.core.LoanApplicationVO;
import in.credable.automation.service.vo.core.ModuleExecutionInfoRequestVO;
import in.credable.automation.service.vo.core.ModuleExecutionInfoResponseVO;
import in.credable.automation.service.vo.core.ModuleExecutionStatusEnum;
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

public class MCAModuleTest extends BaseTest {
    private static final Long AUTOMATION_PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();
    private String vendorId;
    private LoanApplicationVO loanApplicationVO;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor("MCA", "probe42Service");
        vendorId = vendor.getId();
    }

    @BeforeClass(dependsOnMethods = "createVendor")
    public void createLoanApplication() {
        loanApplicationVO = TestHelper.createLoanApplication(AUTOMATION_PROGRAM_ID);
    }

    @Test(description = "Test #888 - Verify that the MCA Module can fetch GSTin associated for a given PAN.")
    public void verifyMCAModuleCanFetchGSTinAssociatedForGivenPAN() {
        Map<String, Object> variables = getVariables();

        ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO = ModuleExecutionInfoRequestVO.builder()
                .moduleCode("mcaData")
                .moduleInstanceName("mcaData")
                .moduleInstanceID("mcaData")
                .clientId(1)
                .userId("1")
                .programId(AUTOMATION_PROGRAM_ID.intValue())
                .variables(variables)
                .moduleConfigData(List.of(getMcaConfigData()))
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

    private Map<String, Object> getVariables() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("pan", RandomDataGenerator.generateRandomPanNumber());
        variables.put("constitution", "PROPRIETORSHIP");
        return variables;
    }

    private Map<String, Object> getMcaConfigData() {
        Map<String, Object> mcaConfigData = new HashMap<>();
        mcaConfigData.put("integrationType", "MCA");
        mcaConfigData.put("vendorCode", vendorId);
        return mcaConfigData;
    }

}
