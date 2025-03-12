package in.credable.automation.service.vo.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.*;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AnchorVO {
    @PodamExclude
    private Long id;

    @PodamStrategyValue(value = ClientCodeStrategy.class)
    private String anchorCode;

    @PodamStrategyValue(value = ClientNameStrategy.class)
    private String anchorName;

    @PodamStringValue(strValue = "CORPORATE")
    private String anchorType;

    @PodamStrategyValue(value = UniqueEmailStrategy.class)
    private String anchorEmail;

    @PodamStringValue(strValue = "AAABB0001C")
    private String businessPAN;

    @PodamStringValue(strValue = "11AAABB0001C1Z5")
    private String gstin;

    @PodamStrategyValue(value = AddressStrategy.class)
    private String anchorAddress;

    @PodamStrategyValue(value = CityNameStrategy.class)
    private String anchorCity;

    @PodamStrategyValue(value = StateNameStrategy.class)
    private String anchorState;

    @PodamStrategyValue(value = CountryNameStrategy.class)
    private String anchorCountry;

    @PodamStrategyValue(value = PinCodeStrategy.class)
    private String anchorPinCode;

    @PodamStrategyValue(value = FullNameStrategy.class)
    private String authorizedPersonName;

    @PodamStrategyValue(value = EmailStrategy.class)
    private String authorizedPersonEmail;

    private MobileNumberVO authorizedPersonMobileNumber;

    @PodamExclude
    private Long clientId;

    @PodamExclude
    private List<AnchorSubSegmentVO> subSegments;

    @PodamStrategyValue(value = ApprovalStatusStrategy.class)
    private ApprovalStatusEnum approvalStatus;

    @PodamExclude
    private Date createdAt;

    @PodamExclude
    private String createdBy;

    @PodamExclude
    private Date updatedAt;

    @PodamExclude
    private String updatedBy;
}
