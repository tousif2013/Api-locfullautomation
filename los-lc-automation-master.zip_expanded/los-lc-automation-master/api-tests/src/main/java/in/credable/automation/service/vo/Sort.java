package in.credable.automation.service.vo;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sorted",
        "empty",
        "unsorted"
})
@Data
public class Sort {

    @JsonProperty("sorted")
    private boolean sorted;

    @JsonProperty("empty")
    private boolean empty;

    @JsonProperty("unsorted")
    private boolean unsorted;

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

