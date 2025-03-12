package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.UuidStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanCreationRequestVO {

    @PodamStrategyValue(UuidStrategy.class)
    private String borrowerId;

    private String moduleCode;

    private String moduleInstanceName;

    private String errorCode;

    private String errorDetails;

    private String flowableProcessInstanceId;

    @PodamExclude
    private String loanNumberFormat;

    @PodamExclude
    private Long programId;
}
