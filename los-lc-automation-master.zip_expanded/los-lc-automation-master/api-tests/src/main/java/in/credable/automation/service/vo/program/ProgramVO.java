package in.credable.automation.service.vo.program;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.*;
import lombok.Data;
import uk.co.jemos.podam.common.PodamCollection;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamLongValue;
import uk.co.jemos.podam.common.PodamStrategyValue;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramVO {
    @PodamExclude
    private Long id;

    @PodamStrategyValue(ProgramCodeStrategy.class)
    private String programCode;

    @PodamStrategyValue(ProgramNameStrategy.class)
    private String programName;

    @PodamLongValue(numValue = "1")
    private Long productCategoryId;

    @PodamLongValue(numValue = "1")
    private Long productTypeId;

    @PodamExclude
    private Long anchorId;

    @PodamExclude
    private String workflowCode;

    @PodamLongValue(numValue = "1")
    private Long countryId;

    @PodamExclude
    private Long clientId;

    @PodamCollection(nbrElements = 1)
    private List<PGNGRuleVO> pgngRules;

    @PodamStrategyValue(RandomBooleanValueStrategy.class)
    private Boolean pgngCheckRequired;

    @PodamStrategyValue(RandomBooleanValueStrategy.class)
    private Boolean makerCheckerApprovalRequired;

    @PodamLongValue(numValue = "1")
    private Long defaultLanguageId;

    @PodamExclude
    private List<Long> languageIds;

    @PodamExclude
    private Long emailServiceProviderId;

    @PodamExclude
    private Long smsServiceProviderId;

    @PodamExclude
    private Long chatBotServiceProviderId;

    @PodamExclude
    private Long marketingAutomationServiceProviderId;

    @PodamStrategyValue(RandomIntegerValueStrategy.class)
    private Integer expiryDays;

    @PodamStrategyValue(RandomIntegerValueStrategy.class)
    private Integer coolOffDays;

    @PodamStrategyValue(FirstNameStrategy.class)
    private String groupCode;

    @PodamExclude
    private ProgramStatus programStatus;

    @PodamExclude
    private String remark;
    @PodamExclude
    private Long anchorSubSegmentId;
    @PodamExclude
    private String programTheme;
    @PodamExclude
    private String ingestionMethod;

}
