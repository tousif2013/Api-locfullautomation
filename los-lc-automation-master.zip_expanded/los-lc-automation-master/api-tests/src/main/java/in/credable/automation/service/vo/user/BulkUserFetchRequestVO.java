package in.credable.automation.service.vo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkUserFetchRequestVO {
    private List<String> userIds;
    private List<String> userNames;
}
