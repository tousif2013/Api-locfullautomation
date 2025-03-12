package in.credable.automation.service.vo.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateVO {
    private String vendorId;
    private String clientId;
    private String templateCode;
    private String appKey;
    private String appId;
    private List<TemplateRequestVO> templateRequestList;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
}
