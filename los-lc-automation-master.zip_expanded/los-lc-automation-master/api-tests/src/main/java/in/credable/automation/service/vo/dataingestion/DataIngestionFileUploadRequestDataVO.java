package in.credable.automation.service.vo.dataingestion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customPropertyMappings",
        "timestamp"
})
@Data
public class DataIngestionFileUploadRequestDataVO {
    @JsonProperty("customPropertyMappings")
    private List<CustomPropertyMappingVO> customPropertyMappings;

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;
}
