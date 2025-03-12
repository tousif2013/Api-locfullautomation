package in.credable.automation.testcases.core;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.core.LosModuleService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.core.*;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.RandomDataGenerator;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BankStatementModuleTest extends BaseTest {
    private static final Long AUTOMATION_PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();
    private final String transactionId = RandomDataGenerator.getUuid();
    private LoanApplicationVO loanApplicationVO;

    @BeforeClass
    public void createLoanApplication() {
        loanApplicationVO = TestHelper.createLoanApplication(AUTOMATION_PROGRAM_ID);
    }

    @Test(description = "Test #867 - Verify that the Financial statement Module can save data for available for setup.")
    public void verifyFinancialStatementModule() {
        FinancialStatementVO financialStatementVO = DataProviderUtil.manufacturePojo(FinancialStatementVO.class);
        List<FinancialStatementVO> bankStatements = new ArrayList<>();
        bankStatements.add(financialStatementVO);

        ModuleExecutionInfoRequestVO moduleExecutionInfoRequestVO = ModuleExecutionInfoRequestVO.builder()
                .moduleCode("bankStatement")
                .moduleInstanceName("bankStatement")
                .moduleInstanceID("bankStatement")
                .clientId(1)
                .userId("1")
                .programId(AUTOMATION_PROGRAM_ID.intValue())
                .variables(Map.of("bank_statements", bankStatements))
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
    }
}
