package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentVO {
    private String type;
    private String url;
}
