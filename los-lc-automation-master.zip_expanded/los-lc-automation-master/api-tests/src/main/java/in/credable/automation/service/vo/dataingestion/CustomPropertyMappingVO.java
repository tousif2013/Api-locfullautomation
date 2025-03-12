package in.credable.automation.service.vo.dataingestion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "standardFieldName",
        "clientSpecificFieldName",
        "fieldValueType",
        "formatType",
        "propertyValidators"
})
@Builder
@Jacksonized
@Getter
@ToString
public class CustomPropertyMappingVO {
    @JsonProperty("standardFieldName")
    private String standardFieldName;

    @JsonProperty("clientSpecificFieldName")
    private String clientSpecificFieldName;

    @JsonProperty("fieldValueType")
    private CustomPropertyFieldValueType fieldValueType;

    @JsonProperty("formatType")
    private FormatType formatType;

    @JsonProperty("propertyValidators")
    private List<PropertyValidatorVO> propertyValidators;
}
