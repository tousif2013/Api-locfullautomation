package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "gstNumber",
        "CREATE_GST_ORDER",
        "gst_order_id",
        "gstOrders",
        "createGstOrderBre"
})
@Builder
@Jacksonized
@Getter
public class ConfirmGstOrderRequestVO {
    @JsonProperty("gstNumber")
    private String gstNumber;
    @JsonProperty("CREATE_GST_ORDER")
    private Object createGstOrder;
    @JsonProperty("gst_order_id")
    private Object gstOrderId;
    @JsonProperty("gstOrders")
    private List<GstOrderVO> gstOrders;
    @JsonProperty("createGstOrderBre")
    private Object createGstOrderBre;
}
