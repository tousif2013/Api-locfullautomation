package in.credable.automation.service.vo.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterAnchorVO {

    private String searchString;
    private String approvalStatus;

}
