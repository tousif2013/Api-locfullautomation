package in.credable.automation.assertions;

import in.credable.automation.assertions.bre.*;
import in.credable.automation.assertions.client.ClientAssertion;
import in.credable.automation.assertions.client.ClientAssertionsFactory;
import in.credable.automation.assertions.client.ClientThemeAssertion;
import in.credable.automation.assertions.core.*;
import in.credable.automation.assertions.dataingestion.DataIngestionAssertionsFactory;
import in.credable.automation.assertions.dataingestion.DataIngestionUploadDataResponseAssertion;
import in.credable.automation.assertions.documentvault.DocumentUploadResponseAssertion;
import in.credable.automation.assertions.documentvault.DocumentVaultAssertionsFactory;
import in.credable.automation.assertions.integration.IntegrationAssertionsFactory;
import in.credable.automation.assertions.integration.VendorAssertion;
import in.credable.automation.assertions.notification.NotificationAssertionsFactory;
import in.credable.automation.assertions.notification.TemplateAssertion;
import in.credable.automation.assertions.program.ProgramAssertion;
import in.credable.automation.assertions.program.ProgramAssertionsFactory;
import in.credable.automation.assertions.program.ProgramThemeAssertion;
import in.credable.automation.assertions.section.SectionAssertion;
import in.credable.automation.assertions.section.SectionAssertionsFactory;
import in.credable.automation.assertions.section.SectionProgramMappingAssertion;
import in.credable.automation.assertions.user.CreateUserAssertion;
import in.credable.automation.assertions.user.UserAssertionsFactory;
import in.credable.automation.service.vo.ErrorResponseVO;
import in.credable.automation.service.vo.Page;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.SuccessResponseVO;
import in.credable.automation.service.vo.bre.BreVO;
import in.credable.automation.service.vo.client.AnchorVO;
import in.credable.automation.service.vo.client.ClientThemeVO;
import in.credable.automation.service.vo.client.ClientVO;
import in.credable.automation.service.vo.core.*;
import in.credable.automation.service.vo.dataingestion.DataIngestionUploadDataResponseVO;
import in.credable.automation.service.vo.documentvault.DocumentUploadResponseVO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.service.vo.module.ModuleInstanceVO;
import in.credable.automation.service.vo.notification.TemplateVO;
import in.credable.automation.service.vo.program.ProgramThemeVO;
import in.credable.automation.service.vo.program.ProgramVO;
import in.credable.automation.service.vo.section.SectionProgramMappingVO;
import in.credable.automation.service.vo.section.SectionVO;
import in.credable.automation.service.vo.user.CreateUserResponseVO;

public final class FrameworkAssertions {
    private FrameworkAssertions() {
    }

    public static ClientAssertion assertThat(ClientVO actual) {
        return ClientAssertionsFactory.createClientAssertion(actual);
    }

    public static ClientThemeAssertion assertThat(ClientThemeVO actual) {
        return ClientAssertionsFactory.createClientThemeAssertion(actual);
    }

    public static ErrorAssertion assertThat(ErrorResponseVO actual) {
        return new ErrorAssertion(actual);
    }

    public static AnchorAssertion assertThat(AnchorVO actual) {
        return new AnchorAssertion(actual);
    }

    public static ProgramAssertion assertThat(ProgramVO actual) {
        return ProgramAssertionsFactory.createProgramAssertion(actual);
    }

    public static ProgramThemeAssertion assertThat(ProgramThemeVO actual) {
        return ProgramAssertionsFactory.createProgramThemeAssertion(actual);
    }

    public static SuccessResponseAssertion assertThat(SuccessResponseVO actual) {
        return new SuccessResponseAssertion(actual);
    }

    public static ModuleInstanceAssertion assertThat(ModuleInstanceVO actual) {
        return new ModuleInstanceAssertion(actual);
    }

    public static <T> ResponseAssertion<T> assertThat(ResponseWO<T> actual) {
        return new ResponseAssertion<>(actual);
    }

    public static CreateUserAssertion assertThat(CreateUserResponseVO actual) {
        return UserAssertionsFactory.createUserAssertion(actual);
    }

    public static UploadDataResponseAssertion assertThat(UploadDataResponseVO actual) {
        return LosCoreAssertionsFactory.createUploadDataResponseAssertion(actual);
    }

    public static UploadSummaryAssertion assertThat(UploadSummaryVO actual) {
        return LosCoreAssertionsFactory.createUploadSummaryAssertion(actual);
    }

    public static <T> PageAssertion<T> assertThat(Page<T> actual) {
        return new PageAssertion<>(actual);
    }

    public static BorrowerResponseAssertion assertThat(BorrowerResponseVO actual) {
        return LosCoreAssertionsFactory.createBorrowerResponseAssertion(actual);
    }

    public static LoanApplicationAssertion assertThat(LoanApplicationVO actual) {
        return LosCoreAssertionsFactory.createLoanApplicationAssertion(actual);
    }

    public static DocumentUploadResponseAssertion assertThat(DocumentUploadResponseVO actual) {
        return DocumentVaultAssertionsFactory.createDocumentUploadResponseAssertion(actual);
    }

    public static VendorAssertion assertThat(VendorVO actual) {
        return IntegrationAssertionsFactory.createVendorAssertion(actual);
    }

    public static DataIngestionUploadDataResponseAssertion assertThat(DataIngestionUploadDataResponseVO actual) {
        return DataIngestionAssertionsFactory.createUploadDataResponseAssertion(actual);
    }

    public static <T> BREAssertion<T> assertThat(BreVO<T> actual) {
        return BREAssertionsFactory.createBREAssertion(actual);
    }

    public static SectionProgramMappingAssertion assertThat(SectionProgramMappingVO actual) {
        return SectionAssertionsFactory.createSectionProgramMappingAssertion(actual);
    }

    public static SectionAssertion assertThat(SectionVO actual) {
        return SectionAssertionsFactory.createSectionAssertion(actual);
    }

    public static ValidateOTPAssertion assertThat(ValidateOTPVO actual) {
        return BREAssertionsFactory.createValidateOTPAssertion(actual);
    }

    public static AuthenticationDataResponseAssertion assertThat(AuthenticationDataResponseVO actual) {
        return BREAssertionsFactory.createAuthenticationDataResponseAssertion(actual);
    }

    public static TokenAssertion assertThat(TokenVO actual) {
        return BREAssertionsFactory.createTokenAssertion(actual);
    }

    public static ModuleExecutionInfoResponseAssertion assertThat(ModuleExecutionInfoResponseVO actual) {
        return LosCoreAssertionsFactory.createModuleExecutionInfoResponseAssertion(actual);
    }

    public static TemplateAssertion assertThat(TemplateVO actual) {
        return NotificationAssertionsFactory.createTemplateAssertion(actual);
    }


}
