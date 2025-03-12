package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStringValue;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorVO {
    @PodamExclude
    private String id;

    private String vendorName;

    private String vendorType;

    private String applicationServiceId;

    private String description;

    @PodamStringValue(strValue = "IND")
    private String countryCode;

    @PodamExclude
    private String secretId;

    @PodamExclude
    private VendorStatusEnum status;

    @PodamExclude
    private Date createdAt;

    @PodamExclude
    private String createdBy;

    @PodamExclude
    private Date updatedAt;

    @PodamExclude
    private String updatedBy;
}
