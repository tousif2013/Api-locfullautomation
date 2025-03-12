package in.credable.automation.service.vo;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "thirdPartyResponse"
})
public class ErrorVO {
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new LinkedHashMap<>();
    private String message;
    private Object thirdPartyResponse;

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

