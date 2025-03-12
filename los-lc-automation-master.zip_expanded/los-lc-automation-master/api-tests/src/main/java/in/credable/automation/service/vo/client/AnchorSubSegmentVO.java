package in.credable.automation.service.vo.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnchorSubSegmentVO {
    @PodamExclude
    private Long id;

    private String subSegmentCode;

    private String subSegmentName;

    @PodamExclude
    private Long anchorId;
}
