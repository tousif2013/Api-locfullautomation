package in.credable.automation.service.vo;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "content",
        "pageable",
        "last",
        "totalPages",
        "totalElements",
        "first",
        "size",
        "number",
        "sort",
        "numberOfElements",
        "empty"
})
@Data
public class Page<T> {

    @JsonProperty("content")
    private List<T> content;
    @JsonProperty("pageable")
    private Pageable pageable;
    @JsonProperty("last")
    private boolean last;
    @JsonProperty("totalPages")
    private int totalPages;
    @JsonProperty("totalElements")
    private int totalElements;
    @JsonProperty("first")
    private boolean first;
    @JsonProperty("size")
    private int size;
    @JsonProperty("number")
    private int number;
    @JsonProperty("sort")
    private Sort sort;
    @JsonProperty("numberOfElements")
    private int numberOfElements;
    @JsonProperty("empty")
    private boolean empty;
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
