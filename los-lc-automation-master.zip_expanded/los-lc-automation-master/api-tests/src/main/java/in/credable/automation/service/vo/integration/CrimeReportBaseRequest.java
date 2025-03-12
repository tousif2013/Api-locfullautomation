package in.credable.automation.service.vo.integration;

import lombok.Data;
import uk.co.jemos.podam.common.PodamBooleanValue;

@Data
public class CrimeReportBaseRequest {
    private String apiKey;
    private String clientRefNo;
    private String reportMode;
    private String priority;
    private String reqTag;
    private String callbackUrl;
    private String ticketSize;

    @PodamBooleanValue(boolValue = true)
    private Boolean crimeWatch;
}

