package in.credable.automation.testcases.core;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.assertions.JsonPathAssertion;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.config.IntegrationDataConfig;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.core.LosModuleService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.core.*;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.RandomDataGenerator;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Prashant Rana
 */
public class PanToGstinModuleTest extends BaseTest {
    private static final Long AUTOMATION_PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();
    private final String transactionId = RandomDataGenerator.getUuid();
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

    @Test(description = "Test #858 - Verify that the PAN to GSTIN Module can fetch GSTin associated for a given PAN.")
    public void verifyFetchingGSTinForGivenPAN() {
        Map<String, Object> moduleConfigData = new HashMap<>();
        moduleConfigData.put("integrationType", "PAN_TO_GSTIN");
        moduleConfigData.put("vendorId", vendorId);

        IntegrationDataConfig integrationDataConfig = ConfigFactory.getIntegrationDataConfig();

        ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO = ModuleExecutionInfoRequestVO.builder()
                .moduleCode("panToGstin")
                .moduleInstanceName("panToGstin")
                .moduleInstanceID("panToGstin")
                .clientId(1)
                .userId("1")
                .programId(AUTOMATION_PROGRAM_ID.intValue())
                .variables(Map.of("pan", integrationDataConfig.getPanToGstinPanNo()))
                .moduleConfigData(List.of(moduleConfigData))
                .loanApplicationId(loanApplicationVO.getId())
                .build();

        Response response = LosModuleService.executeLosModule(moduleExecutionInfoRequestVO, transactionId);
        ResponseWO<ModuleExecutionInfoResponseVO> responseWO = response.as(new TypeRef<>() {
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

        JsonPathAssertion.assertThat(response)
                .assertString("data.executionResult.PAN_TO_GSTIN.status", "SUCCESS")
                .assertString("data.executionResult.PAN_TO_GSTIN.message", "Gstins has been fetched")
                .assertNotNull("data.executionResult.PAN_TO_GSTIN.gstinList")
                .assertListIsNotEmpty("data.executionResult.PAN_TO_GSTIN.gstinList");
    }

    @Test(description = "Test #861 - Verify that the Module Status API can fetch GSTin associated for a given PAN."
            , dependsOnMethods = "verifyFetchingGSTinForGivenPAN"
            , priority = 1)
    public void verifyFetchingModuleStatus() {
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("loanApplicationId", loanApplicationVO.getId());
        queryParams.put("moduleCode", "panToGstin");
        queryParams.put("moduleInstanceId", "panToGstin");
        queryParams.put("transactionId", transactionId);

        Response response = LosModuleService.fetchModuleStatus(queryParams);
        ResponseWO<ModuleStatusVO> responseWO = response.as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("module.status.fetched")
                .hasSameMessage("Module status fetched successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        Assertions.assertThat(responseWO.getData())
                .hasFieldOrPropertyWithValue("status", ModuleExecutionStatusEnum.COMPLETED)
                .hasFieldOrPropertyWithValue("moduleCode", "panToGstin");
    }

    @AfterClass(alwaysRun = true)
    public void changeVendorStatusToInactive() {
        if (StringUtils.isNotBlank(vendorId)) {
            TestHelper.changeVendorStatusToInactive(vendorId);
        }
    }
}
