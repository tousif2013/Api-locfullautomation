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

public class GSTModuleTest extends BaseTest {
    private static final Long AUTOMATION_PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();
    private String vendorId;
    private LoanApplicationVO loanApplicationVO;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor("PAN/GST", "irisService");
        vendorId = vendor.getId();
    }

    @BeforeClass(dependsOnMethods = "createVendor")
    public void createLoanApplication() {
        loanApplicationVO = TestHelper.createLoanApplication(AUTOMATION_PROGRAM_ID);
    }


    @Test(description = "Test #891 - Verify that the Verify Gst Module works correctly when hit from postman")
    public void verifyGSTModule() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("primaryGstin", ConfigFactory.getIntegrationDataConfig().getGstinNo());

        Map<String, Object> moduleConfigData = new HashMap<>();
        moduleConfigData.put("integrationType", "VERIFY_GST");
        moduleConfigData.put("vendorCode", vendorId);

        ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO = ModuleExecutionInfoRequestVO.builder()
                .moduleCode("verifyGst")
                .moduleInstanceName("verifyGst")
                .moduleInstanceID("verifyGst")
                .clientId(1)
                .userId("1")
                .programId(AUTOMATION_PROGRAM_ID.intValue())
                .variables(variables)
                .moduleConfigData(List.of(moduleConfigData))
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

        FrameworkAssertions.assertThat(responseWO.getData())
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
}
