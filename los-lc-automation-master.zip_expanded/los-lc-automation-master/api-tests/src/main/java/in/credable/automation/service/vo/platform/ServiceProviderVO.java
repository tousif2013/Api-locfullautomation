package in.credable.automation.service.vo.platform;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceProviderVO {
    private Long id;
    private String serviceProviderCode;
    private String serviceProviderName;
    private String serviceProviderCategory;
}