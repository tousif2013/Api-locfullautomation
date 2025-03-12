package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUploadRequestDataVO {

    private Integer corporateId;

    private Integer corporateSubsegmentId;

    private Long programId;

    private Date timestamp;

    private String loanNumberFormat;
}
