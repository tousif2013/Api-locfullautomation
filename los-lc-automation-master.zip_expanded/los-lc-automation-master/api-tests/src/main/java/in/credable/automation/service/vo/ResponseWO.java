package in.credable.automation.service.vo;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.credable.automation.deserializer.MillisOrLocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "code",
        "message",
        "timestamp",
        "data"
})
public class ResponseWO<T> {

    @JsonProperty("status")
    private int status;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    @JsonDeserialize(using = MillisOrLocalDateTimeDeserializer.class)

    private LocalDateTime timestamp;

    @JsonProperty("data")
    private T data;

    @JsonProperty("error")
    private ErrorVO error;

    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
