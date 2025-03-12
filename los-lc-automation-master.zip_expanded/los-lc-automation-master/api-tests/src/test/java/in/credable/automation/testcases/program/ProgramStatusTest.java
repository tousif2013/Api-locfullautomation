package in.credable.automation.testcases.program;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.program.ProgramService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.program.ProgramStatus;
import in.credable.automation.service.vo.program.ProgramVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.Test;

public class ProgramStatusTest extends BaseTest {

    private ProgramVO program;

    @Test(description = "Test #908 - verify the functionality of submit program API.")
    public void verifySubmitProgram() {
        ProgramVO programRequestVO = new ProgramVO();
        program = TestHelper.createProgram();
        programRequestVO.setRemark("");

        ResponseWO<ProgramVO> programResponseVO = ProgramService.submitProgram(programRequestVO, program.getId()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(programResponseVO)
                .dataIsNotNull()
                .timestampIsNotNull()
                .hasSameMessage(ApiMessageConstants.SUBMIT_PROGRAM_MESSAGE)
                .hasSameStatus(200)
                .hasSameCode("program.submit.success");
        FrameworkAssertions.assertThat(programResponseVO.getData())
                .programIdIsNotNull()
                .hasSameProgramCode(this.program.getProgramCode())
                .hasSameProgramName(this.program.getProgramName())
                .hasSameClientId(this.program.getClientId())
                .hasSameAnchorId(this.program.getAnchorId())
                .hasSameProgramStatus(ProgramStatus.APPROVAL_PENDING);
    }

    @Test(description = "Test #724 - verify the functionality of Approve program API.", dependsOnMethods = "verifySubmitProgram")
    public void verifyApproveProgram() {
        ProgramVO programRequestVO = new ProgramVO();
        programRequestVO.setRemark("Approving the program");
        ResponseWO<ProgramVO> programResponseVO = ProgramService.approveProgram(programRequestVO, this.program.getId()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(programResponseVO)
                .dataIsNotNull()
                .timestampIsNotNull()
                .hasSameMessage(ApiMessageConstants.APPROVE_PROGRAM_MESSAGE)
                .hasSameStatus(200)
                .hasSameCode("program.approve.success");
        FrameworkAssertions.assertThat(programResponseVO.getData())
                .programIdIsNotNull()
                .hasSameProgramCode(this.program.getProgramCode())
                .hasSameProgramName(this.program.getProgramName())
                .hasSameClientId(this.program.getClientId())
                .hasSameAnchorId(this.program.getAnchorId())
                .hasSameProgramStatus(ProgramStatus.APPROVED)
                .hasSameRemark(programRequestVO.getRemark());
    }

    @Test(description = "Test #730 -verify the functionality of Publish program API.", dependsOnMethods = {"verifySubmitProgram", "verifyApproveProgram"})
    public void verifyPublishProgram() {
        ProgramVO programRequestVO = new ProgramVO();
        programRequestVO.setRemark("");
        ResponseWO<ProgramVO> programResponseVO = ProgramService.publishProgram(programRequestVO, this.program.getId()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(programResponseVO)
                .dataIsNotNull()
                .timestampIsNotNull()
                .hasSameMessage(ApiMessageConstants.PUBLISH_PROGRAM_MESSAGE)
                .hasSameStatus(200)
                .hasSameCode("program.publish.success");
        FrameworkAssertions.assertThat(programResponseVO.getData())
                .programIdIsNotNull()
                .hasSameProgramCode(this.program.getProgramCode())
                .hasSameProgramName(this.program.getProgramName())
                .hasSameClientId(this.program.getClientId())
                .hasSameAnchorId(this.program.getAnchorId())
                .hasSameProgramStatus(ProgramStatus.PUBLISHED);
    }

    @Test(description = "Test #737 -verify the functionality of inactive program API.",
            dependsOnMethods = {"verifySubmitProgram", "verifyApproveProgram", "verifyPublishProgram"})
    public void verifyInactiveProgram() {
        ProgramVO programRequestVO = new ProgramVO();
        programRequestVO.setRemark("Inactivating the program");
        ResponseWO<ProgramVO> programResponseVO = ProgramService.inactiveProgram(programRequestVO, this.program.getId())
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(programResponseVO)
                .dataIsNotNull()
                .timestampIsNotNull()
                .hasSameMessage(ApiMessageConstants.INACTIVE_PROGRAM_MESSAGE)
                .hasSameStatus(200)
                .hasSameCode("program.in-activate.success");
        FrameworkAssertions.assertThat(programResponseVO.getData())
                .programIdIsNotNull()
                .hasSameProgramCode(this.program.getProgramCode())
                .hasSameProgramName(this.program.getProgramName())
                .hasSameClientId(this.program.getClientId())
                .hasSameAnchorId(this.program.getAnchorId())
                .hasSameProgramStatus(ProgramStatus.INACTIVE)
                .hasSameRemark(programRequestVO.getRemark());
    }

    @Test(description = "Test #727 -verify the functionality of Reject program API.", priority = 1)
    public void verifyRejectProgram() {
        ProgramVO program = TestHelper.createProgram();
        Long programId = program.getId();
        ProgramVO programRequestVO = new ProgramVO();
        programRequestVO.setRemark("");
        ProgramService.submitProgram(programRequestVO, programId);
        programRequestVO.setRemark("Rejecting the program.");
        ResponseWO<ProgramVO> programResponseVO = ProgramService.rejectProgram(programRequestVO, programId).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(programResponseVO)
                .dataIsNotNull()
                .timestampIsNotNull()
                .hasSameMessage(ApiMessageConstants.REJECT_PROGRAM_MESSAGE)
                .hasSameStatus(200)
                .hasSameCode("program.reject.success");
        FrameworkAssertions.assertThat(programResponseVO.getData())
                .programIdIsNotNull()
                .hasSameProgramCode(program.getProgramCode())
                .hasSameProgramName(program.getProgramName())
                .hasSameClientId(program.getClientId())
                .hasSameAnchorId(program.getAnchorId())
                .hasSameProgramStatus(ProgramStatus.REJECTED)
                .hasSameRemark(programRequestVO.getRemark());
    }

}