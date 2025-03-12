package in.credable.automation.testcases.core;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GSTOrderModuleTest extends BaseTest {
    public static final String GST_NUMBER = RandomDataGenerator.generateRandomGstNumber();
    public static final String PAN_NUMBER = RandomDataGenerator.generateRandomPanNumber();
    private static final Long AUTOMATION_PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();
    private String vendorId;
    private LoanApplicationVO loanApplicationVO;
    private Map<String, Object> executionResult;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor("PAN/GST", "irisService");
        vendorId = vendor.getId();
    }

    @BeforeClass(dependsOnMethods = "createVendor")
    public void createLoanApplication() {
        loanApplicationVO = TestHelper.createLoanApplication(AUTOMATION_PROGRAM_ID);
    }

    @Test(description = "Test #876 - Verify that the Create Gst Order Module works correctly when hit from postman")
    public void verifyCreateGstOrderModule() {
        Map<String, Object> variables = new HashMap<>(3);
        variables.put("pan", PAN_NUMBER);
        variables.put("name", RandomDataGenerator.generateRandomFullName());
        variables.put("gstNumber", GST_NUMBER);

        GstOrderRequestVO gstOrderRequestVO = getGstOrderRequestVO("SINGLE");

        Map<String, Object> createGstOrderRequest = SerializationUtil.convertObjectToMap(gstOrderRequestVO);

        ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO = ModuleExecutionInfoRequestVO.builder()
                .moduleCode("createGstOrder")
                .moduleInstanceName("createGstOrder")
                .moduleInstanceID("createGstOrder")
                .clientId(1)
                .userId("1")
                .programId(AUTOMATION_PROGRAM_ID.intValue())
                .variables(variables)
                .moduleConfigData(List.of(createGstOrderRequest))
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

    @Test(description = "Test #894 - Verify that the Confirm GST works correctly when hit from postman"
            , dependsOnMethods = "verifyCreateGstOrderModule")
    public void verifyConfirmGSTOrderModule() {
        Object gstOrdersAsJsonObject = executionResult.get("gstOrders");
        Assertions.assertThat(gstOrdersAsJsonObject)
                .as(() -> "gstOrders in execution result is null")
                .isNotNull();
        List<GstOrderVO> gstOrders = SerializationUtil.convertObjectToType(gstOrdersAsJsonObject, new TypeReference<>() {
        });

        gstOrders.forEach(gstOrderVO -> gstOrderVO.setOtpVerified(Boolean.TRUE));
        ConfirmGstOrderRequestVO confirmGstOrderRequestVO = ConfirmGstOrderRequestVO.builder()
                .gstNumber(GST_NUMBER)
                .gstOrders(gstOrders)
                .build();

        Map<String, Object> moduleConfigDataMap = new HashMap<>();
        moduleConfigDataMap.put("integrationType", "CONFIRM_GST_ORDER");
        moduleConfigDataMap.put("vendorCode", vendorId);
        moduleConfigDataMap.put("gstOrderType", "SINGLE");

        ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO = ModuleExecutionInfoRequestVO.builder()
                .moduleCode("confirmGstOrder")
                .moduleInstanceName("confirmGstOrder")
                .moduleInstanceID("confirmGstOrder")
                .clientId(1)
                .userId("1")
                .programId(AUTOMATION_PROGRAM_ID.intValue())
                .variables(SerializationUtil.convertObjectToMap(confirmGstOrderRequestVO))
                .moduleConfigData(List.of(moduleConfigDataMap))
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

        Assertions.assertThat(moduleExecutionInfoResponseVO.getExecutionResult())
                .containsKey("isOrderConfirmed")
                .extracting("isOrderConfirmed")
                .isEqualTo(Boolean.TRUE);
    }

    @Test(description = "Test #879 - Verify that the Create Gst Order Module Multiple works correctly when hit from postman")
    public void verifyCreateMultipleGstOrderModule() {
        Map<String, Object> variables = new HashMap<>(3);
        variables.put("pan", PAN_NUMBER);
        variables.put("name", RandomDataGenerator.generateRandomFullName());
        variables.put("PAN_TO_GSTIN", Map.of("gstinList", List.of(Map.of("gstin", GST_NUMBER))));

        GstOrderRequestVO gstOrderRequestVO = getGstOrderRequestVO("MULTIPLE");

        Map<String, Object> createGstOrderRequest = SerializationUtil.convertObjectToMap(gstOrderRequestVO);
        Map<String, Object> ruleIntegrationMap = getRuleIntegrationMap();

        ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO = ModuleExecutionInfoRequestVO.builder()
                .moduleCode("createGstOrder")
                .moduleInstanceName("createGstOrder")
                .moduleInstanceID("createGstOrder")
                .clientId(1)
                .userId("1")
                .programId(AUTOMATION_PROGRAM_ID.intValue())
                .variables(variables)
                .moduleConfigData(List.of(createGstOrderRequest, ruleIntegrationMap))
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

    private GstOrderRequestVO getGstOrderRequestVO(String orderType) {
        GstSubSection gstSubSection = new GstSubSection();
        gstSubSection.setGstr1(List.of("B2B"));

        return GstOrderRequestVO.builder()
                .integrationType("CREATE_GST_ORDER")
                .vendorCode(vendorId)
                .gstOrderType(orderType)
                .gstForYears("2")
                .gstSubSections(gstSubSection)
                .build();
    }

    private Map<String, Object> getRuleIntegrationMap() {
        RulesIntegrationVO rulesIntegrationVO = new RulesIntegrationVO();
        rulesIntegrationVO.setIntegrationType("RULES");
        RuleVO ruleVO = new RuleVO();
        ruleVO.setId("3ded4211-ceb2-2b2f-4915-d6a9387ddb2a");
        ruleVO.setVersion("1");
        ruleVO.setUrl("");
        ruleVO.setAsync(Boolean.FALSE);
        rulesIntegrationVO.setRules(List.of(ruleVO));
        return SerializationUtil.convertObjectToMap(rulesIntegrationVO);
    }
}
