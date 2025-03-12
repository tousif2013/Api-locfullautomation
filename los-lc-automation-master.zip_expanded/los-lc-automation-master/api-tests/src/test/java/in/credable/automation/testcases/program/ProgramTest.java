package in.credable.automation.testcases.program;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.config.EnvironmentConfig;
import in.credable.automation.service.client.ClientService;
import in.credable.automation.service.program.ProgramService;
import in.credable.automation.service.vo.ErrorResponseVO;
import in.credable.automation.service.vo.SuccessResponseVO;
import in.credable.automation.service.vo.client.ClientFieldMappingVO;
import in.credable.automation.service.vo.program.ProgramClientFieldMappingVO;
import in.credable.automation.service.vo.program.ProgramFieldMappingVO;
import in.credable.automation.service.vo.program.ProgramStatus;
import in.credable.automation.service.vo.program.ProgramVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ProgramTest extends BaseTest {
    private static final EnvironmentConfig ENVIRONMENT_CONFIG = ConfigFactory.getEnvironmentConfig();
    private static final Long AUTOMATION_CLIENT_ID = ENVIRONMENT_CONFIG.getAutomationClientId();
    private static final Long AUTOMATION_ANCHOR_ID = ENVIRONMENT_CONFIG.getAutomationAnchorId();
    private ProgramVO programResponseVO;
    private List<ClientFieldMappingVO> clientFieldMappings;
    private List<Long> clientFieldMappingIdList;

    @Test(description = "Test #83 - Verify if user sends request with valid program data then a new program is created ," +
            "Test#918- Verify that the cool-off period can be set for applications.," +
            "Test#922-Verify that the groupCode can be set for applications.," +
            "Test#916-Verify that the expiry period can be set for applications.")
    public void verifyCreatingNewProgramForClient() {
        ProgramVO programRequestVO = DataProviderUtil.manufacturePojo(ProgramVO.class);
        programRequestVO.setAnchorId(AUTOMATION_ANCHOR_ID);
        programRequestVO.setClientId(AUTOMATION_CLIENT_ID);

        this.programResponseVO = ProgramService.createProgram(programRequestVO).as(ProgramVO.class);

        FrameworkAssertions.assertThat(this.programResponseVO)
                .programIdIsNotNull()
                .hasSameProgramCode(programRequestVO.getProgramCode())
                .hasSameProgramName(programRequestVO.getProgramName())
                .hasSameClientId(programRequestVO.getClientId())
                .hasSameAnchorId(programRequestVO.getAnchorId())
                .hasSameProgramStatus(ProgramStatus.DRAFT)
                .hasSameCoolOffDays(programRequestVO.getCoolOffDays())
                .hasSameGroupCode(programRequestVO.getGroupCode())
                .hasSameExpireDays(programRequestVO.getExpiryDays());
    }

    @Test(description = "Test #84 - Verify if user sends request with duplicate program code then user gets success response"
            , dependsOnMethods = "verifyCreatingNewProgramForClient")
    public void verifyCreatingProgramWithExistingProgramCode() {
        ProgramVO programRequestVO = DataProviderUtil.manufacturePojo(ProgramVO.class);
        programRequestVO.setProgramCode(this.programResponseVO.getProgramCode());
        programRequestVO.setAnchorId(AUTOMATION_ANCHOR_ID);
        programRequestVO.setClientId(AUTOMATION_CLIENT_ID);
        ProgramVO duplicateProgramResponseVO = ProgramService.createProgram(programRequestVO).as(ProgramVO.class);

        FrameworkAssertions.assertThat(duplicateProgramResponseVO)
                .programIdIsNotNull()
                .hasSameProgramCode(programRequestVO.getProgramCode())
                .hasSameProgramName(programRequestVO.getProgramName())
                .hasSameClientId(programRequestVO.getClientId())
                .hasSameAnchorId(programRequestVO.getAnchorId());

        Assertions.assertThat(duplicateProgramResponseVO.getId())
                .as(() -> "Program id should be different for the new program created with same program code")
                .isNotEqualTo(this.programResponseVO.getId());
    }

    @Test(description = "Test #85 - Verify if user sends request to update an existing program with valid data " +
            "then the program is updated")
    public void verifyUpdatingProgram() {
        ProgramVO programRequestVO = DataProviderUtil.manufacturePojo(ProgramVO.class);
        programRequestVO.setAnchorId(AUTOMATION_ANCHOR_ID);
        programRequestVO.setClientId(AUTOMATION_CLIENT_ID);

        ProgramVO programResponseVO = ProgramService.createProgram(programRequestVO).as(ProgramVO.class);

        ProgramVO updateProgramVO = DataProviderUtil.manufacturePojo(ProgramVO.class);
        updateProgramVO.setId(programResponseVO.getId());
        ProgramVO updatedProgramResponseVO = ProgramService.updateProgram(updateProgramVO).as(ProgramVO.class);

        FrameworkAssertions.assertThat(updatedProgramResponseVO)
                .programIdIsNotNull()
                .hasSameProgramId(programResponseVO.getId())
                .hasSameProgramCode(updateProgramVO.getProgramCode())
                .hasSameProgramName(updateProgramVO.getProgramName())
                .hasSameClientId(programResponseVO.getClientId())
                .hasSameAnchorId(programResponseVO.getAnchorId())
                .hasSameProductCategoryId(updateProgramVO.getProductCategoryId())
                .hasSameProductTypeId(updateProgramVO.getProductTypeId())
                .hasSameCountryId(updateProgramVO.getCountryId())
                .hasSamePgngRules(updateProgramVO.getPgngRules())
                .hasSameMakerCheckerApprovalRequired(updateProgramVO.getMakerCheckerApprovalRequired());

        ProgramVO programDetails = ProgramService.getProgramById(updatedProgramResponseVO.getId())
                .as(ProgramVO.class);
        FrameworkAssertions.assertThat(updatedProgramResponseVO)
                .isEqualTo(programDetails);
    }

    @Test(description = "Test #86 - Verify if user sends request to update an existing program with an existing program code " +
            "then user gets success response")
    public void verifyUpdatingProgramWithExistingProgramCode() {
        ProgramVO program1RequestVO = DataProviderUtil.manufacturePojo(ProgramVO.class);
        program1RequestVO.setAnchorId(AUTOMATION_ANCHOR_ID);
        program1RequestVO.setClientId(AUTOMATION_CLIENT_ID);

        ProgramVO program1 = ProgramService.createProgram(program1RequestVO).as(ProgramVO.class);

        ProgramVO program2RequestVO = DataProviderUtil.manufacturePojo(ProgramVO.class);
        program2RequestVO.setAnchorId(AUTOMATION_ANCHOR_ID);
        program2RequestVO.setClientId(AUTOMATION_CLIENT_ID);

        ProgramVO program2 = ProgramService.createProgram(program2RequestVO).as(ProgramVO.class);

        ProgramVO updateProgram2RequestVO = new ProgramVO();
        updateProgram2RequestVO.setId(program2.getId());
        updateProgram2RequestVO.setProgramCode(program1.getProgramCode());

        ProgramVO updatedProgram2 = ProgramService.updateProgram(updateProgram2RequestVO).as(ProgramVO.class);

        FrameworkAssertions.assertThat(updatedProgram2)
                .programIdIsNotNull()
                .hasSameProgramId(program2.getId())
                .hasSameProgramCode(program1.getProgramCode())
                .hasSameProgramName(program2.getProgramName())
                .hasSameClientId(program2.getClientId())
                .hasSameAnchorId(program2.getAnchorId())
                .hasSameProductCategoryId(program2.getProductCategoryId())
                .hasSameProductTypeId(program2.getProductTypeId())
                .hasSameCountryId(program2.getCountryId())
                .hasSamePgngRules(program2.getPgngRules())
                .hasSameMakerCheckerApprovalRequired(program2.getMakerCheckerApprovalRequired());

        ProgramVO programDetails = ProgramService.getProgramById(updatedProgram2.getId())
                .as(ProgramVO.class);
        FrameworkAssertions.assertThat(updatedProgram2)
                .isEqualTo(programDetails);
    }

    @Test(description = "Test #87 - Verify if user sends request to get an existing program then user gets program data successfully"
            , dependsOnMethods = "verifyCreatingNewProgramForClient")
    public void verifyGettingExistingProgramDetails() {
        ProgramVO programDetails = ProgramService.getProgramById(this.programResponseVO.getId())
                .as(ProgramVO.class);
        FrameworkAssertions.assertThat(programDetails)
                .isEqualTo(this.programResponseVO);
    }

    @Test(description = "Test #88 - Verify if user sends request to get a non-existing program then user gets error response")
    public void verifyGettingNotExistingProgramDetails() {
        long invalidProgramId = -1L;
        ErrorResponseVO errorResponseVO = ProgramService.getProgramById(invalidProgramId, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Program id:%d not found".formatted(invalidProgramId));
    }

    @Test(description = "Test #89 - Verify if user sends request to add program client field mappings for an existing program with valid data " +
            "then user gets success response"
            , dependsOnMethods = "verifyCreatingNewProgramForClient")
    public void verifyAddingProgramClientFieldMapping() {
        // Get client field mappings
        clientFieldMappings = ClientService.getClientFieldMappings(AUTOMATION_CLIENT_ID)
                .as(new TypeRef<>() {
                });

        Assertions.assertThat(clientFieldMappings)
                .as(() -> "Client field mappings should not be null for automation client with id:" + AUTOMATION_CLIENT_ID)
                .isNotNull()
                .as(() -> "Client field mappings should not be empty for automation client with id:" + AUTOMATION_CLIENT_ID)
                .isNotEmpty();

        // Extract mapping ids
        clientFieldMappingIdList = clientFieldMappings.stream()
                .map(ClientFieldMappingVO::getId)
                .toList();

        // Add program client field mappings
        List<ProgramFieldMappingVO> programFieldMappings = new ArrayList<>();
        for (Long clientFieldMappingId : clientFieldMappingIdList) {
            ProgramFieldMappingVO programFieldMappingVO = new ProgramFieldMappingVO();
            programFieldMappingVO.setClientFieldMappingId(clientFieldMappingId);
            programFieldMappingVO.setMandatory(true);
            programFieldMappings.add(programFieldMappingVO);
        }
        SuccessResponseVO successResponseVO = ProgramService.addProgramClientFieldMappings(this.programResponseVO.getId(),
                        programFieldMappings)
                .as(SuccessResponseVO.class);

        FrameworkAssertions.assertThat(successResponseVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameResponseMessage("Field Mappings created for program with id:" + this.programResponseVO.getId());
    }

    @Test(description = "Test #90 - Verify if user sends multiple request to map client field for a same program " +
            "then user gets error response"
            , dependsOnMethods = "verifyAddingProgramClientFieldMapping")
    public void verifyAddingDuplicateClientFieldMapping() {
        List<ProgramFieldMappingVO> programFieldMappings = new ArrayList<>();
        for (Long clientFieldMappingId : clientFieldMappingIdList) {
            ProgramFieldMappingVO programFieldMappingVO = new ProgramFieldMappingVO();
            programFieldMappingVO.setClientFieldMappingId(clientFieldMappingId);
            programFieldMappingVO.setMandatory(true);
            programFieldMappings.add(programFieldMappingVO);
        }
        ErrorResponseVO errorResponseVO = ProgramService.addProgramClientFieldMappings(this.programResponseVO.getId(),
                        programFieldMappings,
                        StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("EXCEPTION");
    }

    @Test(description = "Test #91 - Verify if user sends request to get program client field mappings for an existing program with valid data " +
            "then user gets program client field mappings"
            , dependsOnMethods = "verifyAddingProgramClientFieldMapping")
    public void verifyGettingProgramClientFieldMapping() {
        List<ProgramClientFieldMappingVO> programClientFieldMappings = ProgramService.getProgramClientFieldMappings(this.programResponseVO.getId())
                .as(new TypeRef<>() {
                });

        Assertions.assertThat(programClientFieldMappings)
                .as(() -> "Program id is not expected in response of getting program client field mappings")
                .allMatch(programClientFieldMappingVO -> programClientFieldMappingVO.getProgramId() != null
                        && programClientFieldMappingVO.getProgramId().equals(this.programResponseVO.getId()));

        Assertions.assertThat(programClientFieldMappings)
                .as(() -> "Program client field mappings is not created as expected")
                .hasSameSizeAs(this.clientFieldMappings)
                .extracting(ProgramClientFieldMappingVO::getClientFieldMappingVO)
                .containsAll(this.clientFieldMappings);
    }
}
