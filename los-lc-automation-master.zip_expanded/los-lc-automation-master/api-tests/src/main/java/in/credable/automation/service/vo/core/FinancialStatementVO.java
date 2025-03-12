package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import uk.co.jemos.podam.common.PodamCollection;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "files"
})
@Data
public class FinancialStatementVO {
    @JsonProperty("files")
    @PodamCollection(nbrElements = 1)
    private List<FileVO> files;
}
