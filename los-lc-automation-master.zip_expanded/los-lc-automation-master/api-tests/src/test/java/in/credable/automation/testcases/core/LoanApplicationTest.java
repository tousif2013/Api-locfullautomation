package in.credable.automation.testcases.core;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.core.LoanApplicationService;
import in.credable.automation.service.vo.ErrorResponseVO;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.core.LoanApplicationStatusEnum;
import in.credable.automation.service.vo.core.LoanApplicationVO;
import in.credable.automation.service.vo.core.LoanCreationRequestVO;
import in.credable.automation.service.vo.core.LoanUpdationRequestVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.Test;

import java.util.List;

public class LoanApplicationTest extends BaseTest {
    private static final Long AUTOMATION_PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();
    private static final String LOAN_APPLICATION_NUMBER_PREFIX = "VF_";
    private LoanApplicationVO loanApplicationVO;

    @Test(description = "Test #327 - Verify the functionality of Create loan application API")
    public void verifyCreatingNewLoanApplication() {
        LoanCreationRequestVO loanCreationRequestVO = DataProviderUtil.manufacturePojo(LoanCreationRequestVO.class);
        loanCreationRequestVO.setLoanNumberFormat(LOAN_APPLICATION_NUMBER_PREFIX + "%s");
        loanCreationRequestVO.setProgramId(AUTOMATION_PROGRAM_ID);

        ResponseWO<LoanApplicationVO> responseWO = LoanApplicationService.createLoanApplication(loanCreationRequestVO)
                .as(new TypeRef<>() {
                });

        assertSuccessResponseForCreationOfNewLoanApplication(responseWO, loanCreationRequestVO);
        this.loanApplicationVO = responseWO.getData();
    }

    @Test(description = "Test #330 - Verify the functionality of fetch loan applications using borrower id API"
            , dependsOnMethods = "verifyCreatingNewLoanApplication"
            , priority = 1)
    public void verifyFetchingLoanApplicationsUsingBorrowerId() {
        ResponseWO<List<LoanApplicationVO>> responseWO = LoanApplicationService.getAllLoanApplicationsForBorrower(loanApplicationVO.getBorrowerId())
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("loan.details.fetched")
                .hasSameMessage("Loan application details fetched successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        List<LoanApplicationVO> loanApplicationVOS = responseWO.getData();

        FrameworkAssertions.assertThat(loanApplicationVOS.getFirst())
                .isEqualTo(loanApplicationVO);
    }

    @Test(description = "Test #333 - Verify the functionality of fetch loan applications detail API"
            , dependsOnMethods = "verifyCreatingNewLoanApplication"
            , priority = 2)
    public void verifyFetchingLoanApplicationDetailsUsingLoanApplicationId() {
        ResponseWO<LoanApplicationVO> responseWO = LoanApplicationService.getLoanApplicationDetailsById(loanApplicationVO.getId())
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("loan.details.fetched")
                .hasSameMessage("Loan application details fetched successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        LoanApplicationVO loanApplicationVO = responseWO.getData();

        FrameworkAssertions.assertThat(loanApplicationVO)
                .isEqualTo(this.loanApplicationVO);
    }

    @Test(description = "Test #336 - Verify the functionality of update loan application API"
            , dependsOnMethods = "verifyCreatingNewLoanApplication"
            , priority = 3)
    public void verifyUpdatingLoanApplication() {
        LoanUpdationRequestVO loanUpdationRequestVO = DataProviderUtil.manufacturePojo(LoanUpdationRequestVO.class);
        String loanApplicationId = loanApplicationVO.getId();

        ResponseWO<LoanApplicationVO> responseWO = LoanApplicationService.updateLoanApplication(loanApplicationId, loanUpdationRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("loan.updated")
                .hasSameMessage("Loan application updated successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        LoanApplicationVO updatedLoanApplicationVO = responseWO.getData();

        FrameworkAssertions.assertThat(updatedLoanApplicationVO)
                .hasSameId(this.loanApplicationVO.getId())
                .hasSameLoanNumber(this.loanApplicationVO.getLoanNumber())
                .hasSameStatus(loanUpdationRequestVO.getStatus().name())
                .hasSameProgramId(this.loanApplicationVO.getProgramId())
                .hasSameBorrowerId(this.loanApplicationVO.getBorrowerId())
                .hasSameModuleCode(loanUpdationRequestVO.getModuleCode())
                .hasSameModuleInstanceName(loanUpdationRequestVO.getModuleInstanceName())
                .hasSameFlowableProcessInstanceId(loanUpdationRequestVO.getFlowableProcessInstanceId())
                .hasSameErrorCode(loanUpdationRequestVO.getErrorCode())
                .hasSameErrorDetails(loanUpdationRequestVO.getErrorDetails());
    }

    @Test(description = "Test #505 - Verify the functionality of create loan application API."
            , priority = 4)
    public void verifyCreatingLoanApplicationForExistingBorrower() {
        LoanApplicationVO loanApplicationVO = TestHelper.createLoanApplication(AUTOMATION_PROGRAM_ID);
        LoanCreationRequestVO loanCreationRequestVO = DataProviderUtil.manufacturePojo(LoanCreationRequestVO.class);
        loanCreationRequestVO.setBorrowerId(loanApplicationVO.getBorrowerId());
        loanCreationRequestVO.setProgramId(AUTOMATION_PROGRAM_ID);
        loanCreationRequestVO.setLoanNumberFormat(LOAN_APPLICATION_NUMBER_PREFIX + "%s");

        /*
         1. Trigger the create loan application API with valid input when the previous application was CREATED
         400 Bad Request status should be received in the response
         New loan application should not be created
        */
        ErrorResponseVO errorResponseVO = LoanApplicationService.createLoanApplication(loanCreationRequestVO, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        assertErrorResponseVOForDuplicateLoanApplication(errorResponseVO, loanApplicationVO.getLoanNumber());

        // 2. Update the loan application status to IN_PROGRESS
        updateLoanApplicationStatus(loanApplicationVO.getId(), LoanApplicationStatusEnum.IN_PROGRESS);

        /*
         3. Trigger the create loan application API with valid input when the previous application was IN_PROGRESS
         400 Bad Request status should be received in the response
         New loan application should not be created
        */
        errorResponseVO = LoanApplicationService.createLoanApplication(loanCreationRequestVO, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });
        assertErrorResponseVOForDuplicateLoanApplication(errorResponseVO, loanApplicationVO.getLoanNumber());

        // 4. Update the loan application status to BORROWER_APPROVAL_PENDING_BY_ADMIN
        updateLoanApplicationStatus(loanApplicationVO.getId(), LoanApplicationStatusEnum.BORROWER_APPROVAL_PENDING_BY_ADMIN);

        /*
        5. Trigger the create loan application API with valid input when the previous application was BORROWER_APPROVAL_PENDING_BY_ADMIN
        400 Bad Request status should be received in the response
        New loan application should not be created
        */
        errorResponseVO = LoanApplicationService.createLoanApplication(loanCreationRequestVO, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });
        assertErrorResponseVOForDuplicateLoanApplication(errorResponseVO, loanApplicationVO.getLoanNumber());

        // 6. Update the loan application status to FAILED
        updateLoanApplicationStatus(loanApplicationVO.getId(), LoanApplicationStatusEnum.FAILED);

        /*
        7. Trigger the create loan application API with valid input when the previous application was FAILED
        200 OK status should be received in the response
        New loan application should be created
        */
        ResponseWO<LoanApplicationVO> responseWO = LoanApplicationService.createLoanApplication(loanCreationRequestVO)
                .as(new TypeRef<>() {
                });
        assertSuccessResponseForCreationOfNewLoanApplication(responseWO, loanCreationRequestVO);
        loanApplicationVO = responseWO.getData();

        // 8. Update the loan application status to COMPLETED
        updateLoanApplicationStatus(loanApplicationVO.getId(), LoanApplicationStatusEnum.COMPLETED);

        /*
        9. Trigger the create loan application API with valid input when the previous application was COMPLETED
        400 Bad Request status should be received in the response
        New loan application should not be created
        */
        errorResponseVO = LoanApplicationService.createLoanApplication(loanCreationRequestVO, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });
        assertErrorResponseVOForDuplicateLoanApplication(errorResponseVO, loanApplicationVO.getLoanNumber());

        // 10. Update the loan application status to EXPIRED
        updateLoanApplicationStatus(loanApplicationVO.getId(), LoanApplicationStatusEnum.EXPIRED);

        /*
        11. Trigger the create loan application API with valid input when the previous application was EXPIRED
        200 OK status should be received in the response
        New loan application should be created
        */
        responseWO = LoanApplicationService.createLoanApplication(loanCreationRequestVO)
                .as(new TypeRef<>() {
                });
        assertSuccessResponseForCreationOfNewLoanApplication(responseWO, loanCreationRequestVO);
        loanApplicationVO = responseWO.getData();

        // 12. Update the loan application status to SYSTEM_ERROR
        updateLoanApplicationStatus(loanApplicationVO.getId(), LoanApplicationStatusEnum.SYSTEM_ERROR);

        /*
        13. Trigger the create loan application API with valid input when the previous application was SYSTEM_ERROR
        200 OK status should be received in the response
        New loan application should be created
        */
        responseWO = LoanApplicationService.createLoanApplication(loanCreationRequestVO)
                .as(new TypeRef<>() {
                });
        assertSuccessResponseForCreationOfNewLoanApplication(responseWO, loanCreationRequestVO);
        loanApplicationVO = responseWO.getData();

        // 14. Update the loan application status to REJECTED
        updateLoanApplicationStatus(loanApplicationVO.getId(), LoanApplicationStatusEnum.REJECTED);

        // 15. Trigger the API with valid input when the previous application was REJECTED
        responseWO = LoanApplicationService.createLoanApplication(loanCreationRequestVO)
                .as(new TypeRef<>() {
                });

        assertSuccessResponseForCreationOfNewLoanApplication(responseWO, loanCreationRequestVO);
    }

    private void updateLoanApplicationStatus(String loanApplicationId, LoanApplicationStatusEnum loanApplicationStatusEnum) {
        LoanUpdationRequestVO loanUpdationRequestVO = new LoanUpdationRequestVO();
        loanUpdationRequestVO.setStatus(loanApplicationStatusEnum);
        loanUpdationRequestVO.setUpdatedBy("Automation Suite");

        ResponseWO<LoanApplicationVO> responseWO = LoanApplicationService.updateLoanApplication(loanApplicationId, loanUpdationRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("loan.updated")
                .hasSameMessage("Loan application updated successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        LoanApplicationVO updatedLoanApplicationVO = responseWO.getData();

        FrameworkAssertions.assertThat(updatedLoanApplicationVO)
                .hasSameId(loanApplicationId)
                .hasSameStatus(loanApplicationStatusEnum.name());
    }

    private void assertErrorResponseVOForDuplicateLoanApplication(ErrorResponseVO errorResponseVO, String loanNumber) {
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("loan.dup.exp.02")
                .hasSameErrorMessage("Loan application with the number %s already exists".formatted(loanNumber))
                .timestampIsNotNull();
    }

    private void assertSuccessResponseForCreationOfNewLoanApplication(ResponseWO<LoanApplicationVO> responseWO,
                                                                      LoanCreationRequestVO loanCreationRequestVO) {
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(201)
                .hasSameCode("loan.created")
                .hasSameMessage("Loan application created successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        FrameworkAssertions.assertThat(responseWO.getData())
                .loanApplicationIdIsNotNull()
                .loanNumberIsNotNull()
                .loanNumberStartsWith(LOAN_APPLICATION_NUMBER_PREFIX)
                .hasSameProgramId(loanCreationRequestVO.getProgramId())
                .hasSameBorrowerId(loanCreationRequestVO.getBorrowerId())
                .statusIsCreated()
                .hasSameModuleCode(loanCreationRequestVO.getModuleCode())
                .hasSameModuleInstanceName(loanCreationRequestVO.getModuleInstanceName())
                .hasSameErrorCode(loanCreationRequestVO.getErrorCode())
                .hasSameErrorDetails(loanCreationRequestVO.getErrorDetails())
                .hasSameFlowableProcessInstanceId(loanCreationRequestVO.getFlowableProcessInstanceId());
    }
}
