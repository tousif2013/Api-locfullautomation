package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListBorrowersRequestVO {

    private Long programId;

    private BorrowerStatusEnum approvalStatus;

    private List<BorrowerStatusEnum> excludeStatuses;

    private BorrowerTypeEnum borrowerType;

    private String searchString;

}
