package in.credable.automation.service.vo.dataingestion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "errorMessage",
        "regexPattern",
        "start",
        "startInclusive",
        "end",
        "endInclusive"
})
@Builder
@Jacksonized
@Getter
@ToString
public class PropertyValidatorVO {
    @JsonProperty("type")
    private PropertyValidatorType type;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("regexPattern")
    private String regexPattern;

    @JsonProperty("start")
    private Number start;

    @JsonProperty("startInclusive")
    private Boolean startInclusive;

    @JsonProperty("end")
    private Number end;

    @JsonProperty("endInclusive")
    private Boolean endInclusive;
}
