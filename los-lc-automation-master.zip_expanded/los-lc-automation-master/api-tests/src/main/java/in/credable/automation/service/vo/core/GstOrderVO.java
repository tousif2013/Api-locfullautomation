package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "transaction_id",
        "timer",
        "otp_verified",
        "resend_otp",
        "otp_sent",
        "otp",
        "send_otp",
        "gstin",
        "username",
        "submit_otp",
        "order_confirmed",
        "report_received"
})
@Data
public class GstOrderVO {
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("timer")
    private Integer timer;
    @JsonProperty("otp_verified")
    private Boolean otpVerified;
    @JsonProperty("resend_otp")
    private Boolean resendOtp;
    @JsonProperty("otp_sent")
    private Boolean otpSent;
    @JsonProperty("otp")
    private Object otp;
    @JsonProperty("send_otp")
    private Boolean sendOtp;
    @JsonProperty("gstin")
    private String gstin;
    @JsonProperty("username")
    private Object username;
    @JsonProperty("submit_otp")
    private Boolean submitOtp;
    @JsonProperty("order_confirmed")
    private Boolean orderConfirmed;
    @JsonProperty("report_received")
    private Boolean reportReceived;
}
