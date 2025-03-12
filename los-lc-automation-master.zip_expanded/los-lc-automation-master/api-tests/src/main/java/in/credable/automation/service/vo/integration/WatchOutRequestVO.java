package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WatchOutRequestVO implements Serializable {
    private String defaulterName;
    private String defaulterType;
    private String searchType;
    private String panCinDinNumber;
    private Integer uniqueId;
    private String internalRefId;
}
