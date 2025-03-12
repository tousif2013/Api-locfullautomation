package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "integrationType",
        "vendorCode",
        "gstOrderType",
        "gstForYears",
        "gstSubSections"
})
@Builder
@Jacksonized
@Getter
@ToString
public class GstOrderRequestVO {
    @JsonProperty("integrationType")
    private String integrationType;
    @JsonProperty("vendorCode")
    private String vendorCode;
    @JsonProperty("gstOrderType")
    private String gstOrderType;
    @JsonProperty("gstForYears")
    private String gstForYears;
    @JsonProperty("gstSubSections")
    private Object gstSubSections;
}
