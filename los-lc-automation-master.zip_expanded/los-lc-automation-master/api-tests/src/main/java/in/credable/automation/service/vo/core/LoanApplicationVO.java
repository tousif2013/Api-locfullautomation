package in.credable.automation.service.vo.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class LoanApplicationVO {
    private String id;
    private String loanNumber;
    private String status;
    private Long programId;
    private String borrowerId;
    private String workflowCode;
    private String progress;
    private String internalProgress;
    private String uniqueIdentifier;
    private String rejectionReason;
    private String moduleCode;
    private String moduleInstanceName;
    private String errorCode;
    private String errorDetails;
    private String flowableProcessInstanceId;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime checkedAt;
    private String checkedBy;
    private List<AttachmentVO> attachments;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        LoanApplicationVO that = (LoanApplicationVO) object;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(loanNumber, that.loanNumber)) return false;
        if (!Objects.equals(status, that.status)) return false;
        if (!Objects.equals(programId, that.programId)) return false;
        if (!Objects.equals(borrowerId, that.borrowerId)) return false;
        if (!Objects.equals(workflowCode, that.workflowCode)) return false;
        if (!Objects.equals(progress, that.progress)) return false;
        if (!Objects.equals(rejectionReason, that.rejectionReason))
            return false;
        if (!Objects.equals(moduleCode, that.moduleCode)) return false;
        if (!Objects.equals(moduleInstanceName, that.moduleInstanceName))
            return false;
        if (!Objects.equals(errorCode, that.errorCode)) return false;
        if (!Objects.equals(errorDetails, that.errorDetails)) return false;
        if (!Objects.equals(flowableProcessInstanceId, that.flowableProcessInstanceId))
            return false;
        if (!isSameSecond(createdAt, that.createdAt)) return false;
        if (!Objects.equals(createdBy, that.createdBy)) return false;
        if (!isSameSecond(updatedAt, that.updatedAt)) return false;
        if (!Objects.equals(updatedBy, that.updatedBy)) return false;
        if (!Objects.equals(checkedAt, that.checkedAt)) return false;
        if (!Objects.equals(attachments, that.attachments)) return false;
        return Objects.equals(checkedBy, that.checkedBy);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (loanNumber != null ? loanNumber.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (programId != null ? programId.hashCode() : 0);
        result = 31 * result + (borrowerId != null ? borrowerId.hashCode() : 0);
        result = 31 * result + (workflowCode != null ? workflowCode.hashCode() : 0);
        result = 31 * result + (progress != null ? progress.hashCode() : 0);
        result = 31 * result + (rejectionReason != null ? rejectionReason.hashCode() : 0);
        result = 31 * result + (moduleCode != null ? moduleCode.hashCode() : 0);
        result = 31 * result + (moduleInstanceName != null ? moduleInstanceName.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (errorDetails != null ? errorDetails.hashCode() : 0);
        result = 31 * result + (flowableProcessInstanceId != null ? flowableProcessInstanceId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        result = 31 * result + (checkedAt != null ? checkedAt.hashCode() : 0);
        result = 31 * result + (checkedBy != null ? checkedBy.hashCode() : 0);
        result = 31 * result + (attachments != null ? attachments.hashCode() : 0);
        return result;
    }

    private boolean isSameSecond(LocalDateTime ldt1, LocalDateTime ldt2) {
        return ldt1.truncatedTo(ChronoUnit.SECONDS).isEqual(ldt2.truncatedTo(ChronoUnit.SECONDS));
    }

}
