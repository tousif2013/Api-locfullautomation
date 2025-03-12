package in.credable.automation.service.vo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserResponseVO {
    private String userId;
    private String userName;
}
