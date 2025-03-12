package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleVO {
    private String id;
    private String version;
    private String url;
    private Boolean async;
}
