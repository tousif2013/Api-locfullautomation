package in.credable.automation.service.vo.platform;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductTypesVO {
    private Long id;
    private String productTypeCode;
    private String productTypeName;
    private String categoryCode;
}