package in.credable.automation.ui.pages.modals.corporate;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import in.credable.automation.service.vo.client.AnchorSubSegmentVO;
import in.credable.automation.service.vo.client.AnchorVO;
import in.credable.automation.service.vo.client.MobileNumberVO;
import in.credable.automation.ui.pages.modals.BaseModal;
import in.credable.automation.ui.pages.modals.common.ConfirmationModal;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CorporateDetailsModal extends BaseModal {
    private static final SelenideElement CLOSE_BUTTON = $(byCssSelector(".close_dealer_view"));
    private static final SelenideElement CORPORATE_ID = $(byCssSelector(".dealer_code"));
    private static final SelenideElement CORPORATE_NAME = $(byCssSelector(".company_name"));
    private static final ElementsCollection CORPORATE_DETAIL_HEADERS = $$(byCssSelector(".customer_detail_header"));
    private static final ElementsCollection CORPORATE_DETAIL_TITLES = $$(byCssSelector("div.customer_detail > div.customer_detail_title"));
    private static final Function<String, String> GET_FIELD_VALUE_BY_TITLE = "//div[@class='customer_detail_title' and text()='%s']/following-sibling::div"::formatted;
    private static final Function<String, SelenideElement> FIELD_VALUE_BY_TITLE = fieldName ->
            $(byXpath(GET_FIELD_VALUE_BY_TITLE.apply(fieldName)));
    private static final SelenideElement CORPORATE_ADDRESS = $(byCssSelector(".customer_add"));
    private static final ElementsCollection SUBSEGMENT_NAMES = $$(byXpath(GET_FIELD_VALUE_BY_TITLE.apply("Corporate Subsegment")));
    private static final ElementsCollection SUBSEGMENT_CODES = $$(byXpath(GET_FIELD_VALUE_BY_TITLE.apply("Corporate Subsegment Code")));
    private static final SelenideElement APPROVE_BUTTON = $(byCssSelector("div.dealer_approve"));
    private static final SelenideElement REJECT_BUTTON = $(byCssSelector("div.dealer_reject"));

    @Override
    public void closeModal() {
        CLOSE_BUTTON.scrollTo().click();
    }

    public String getCorporateProfileLabelValue() {
        return CORPORATE_DETAIL_HEADERS.shouldBe(CollectionCondition.sizeGreaterThan(0)).first().getText();
    }

    public String getCorporateAddressLabelValue() {
        return CORPORATE_DETAIL_HEADERS.get(1).getText();
    }

    public String getCorporateSubSegmentLabelValue() {
        return CORPORATE_DETAIL_HEADERS.get(2).getText();
    }

    public String getAuthorizedPersonDetailsLabelValue() {
        return CORPORATE_DETAIL_HEADERS.get(3).getText();
    }

    public String getCorporateNameLabelValue() {
        return CORPORATE_DETAIL_TITLES.shouldBe(CollectionCondition.sizeGreaterThan(0)).get(0).getText();
    }

    public String getCorporateEmailLabelValue() {
        return CORPORATE_DETAIL_TITLES.get(1).getText();
    }

    public String getCorporatePANLabelValue() {
        return CORPORATE_DETAIL_TITLES.get(2).getText();
    }

    public String getCorporateGSTLabelValue() {
        return CORPORATE_DETAIL_TITLES.get(3).getText();
    }

    public String getAuthorizedPersonNameInputPlaceholder() {
        if (CORPORATE_DETAIL_TITLES.size() > 7) {
            return CORPORATE_DETAIL_TITLES.get(6).getText();
        }
        return CORPORATE_DETAIL_TITLES.get(4).getText();
    }

    public String getAuthorizedPersonPhoneInputPlaceholder() {
        if (CORPORATE_DETAIL_TITLES.size() > 7) {
            return CORPORATE_DETAIL_TITLES.get(7).getText();
        }
        return CORPORATE_DETAIL_TITLES.get(5).getText();
    }

    public String getAuthorizedPersonEmailInputPlaceholder() {
        if (CORPORATE_DETAIL_TITLES.size() > 7) {
            return CORPORATE_DETAIL_TITLES.get(8).getText();
        }
        return CORPORATE_DETAIL_TITLES.get(6).getText();
    }

    public AnchorVO getCorporateDetails() {
        AnchorVO anchorVO = new AnchorVO();
        anchorVO.setId(getCorporateId());
        anchorVO.setAnchorName(getCorporateName());
        anchorVO.setAnchorEmail(getCorporateEmailAddress());
        anchorVO.setBusinessPAN(getBusinessPAN());
        anchorVO.setGstin(getGstin());
        anchorVO.setAnchorAddress(getCorporateAddress());
        populateSubSegmentDetails(anchorVO);
        anchorVO.setAuthorizedPersonName(getAuthorizedPersonName());
        String authorizedPersonMobileWithDialingCode = getAuthorizedPersonMobile();
        MobileNumberVO mobileNumberVO = new MobileNumberVO(authorizedPersonMobileWithDialingCode, ' ');
        anchorVO.setAuthorizedPersonMobileNumber(mobileNumberVO);
        anchorVO.setAuthorizedPersonEmail(getAuthorizedPersonEmail());
        return anchorVO;
    }

    public void approveCorporate() {
        APPROVE_BUTTON.scrollTo().click();
        ConfirmationModal confirmationModal = new ConfirmationModal();
        confirmationModal.selectYes();
    }

    public String getApproveButtonText() {
        return APPROVE_BUTTON.getText();
    }

    public void rejectCorporate() {
        REJECT_BUTTON.scrollTo().click();
        ConfirmationModal confirmationModal = new ConfirmationModal();
        confirmationModal.selectYes();
    }

    public String getRejectButtonText() {
        return REJECT_BUTTON.getText();
    }

    private Long getCorporateId() {
        String corporateIdText = CORPORATE_ID.shouldBe(not(empty)).getText();
        return Long.parseLong(StringUtils.trimToEmpty(StringUtils.split(corporateIdText, ":")[1]));
    }

    private String getCorporateName() {
        return CORPORATE_NAME.getText();
    }

    private String getCorporateEmailAddress() {
        return getFieldValue("Corporate Email");
    }

    private String getBusinessPAN() {
        return getFieldValue("PAN");
    }

    private String getGstin() {
        return getFieldValue("GSTIN");
    }

    private String getCorporateAddress() {
        return CORPORATE_ADDRESS.getText();
    }

    private void populateSubSegmentDetails(AnchorVO anchorVO) {
        if (!SUBSEGMENT_NAMES.isEmpty()) {
            List<AnchorSubSegmentVO> subsegments = new ArrayList<>();
            for (int i = 0; i < SUBSEGMENT_NAMES.size(); i++) {
                AnchorSubSegmentVO anchorSubSegmentVO = new AnchorSubSegmentVO();
                anchorSubSegmentVO.setSubSegmentName(SUBSEGMENT_NAMES.get(i).getText());
                anchorSubSegmentVO.setSubSegmentCode(SUBSEGMENT_CODES.get(i).getText());
                subsegments.add(anchorSubSegmentVO);
            }
            anchorVO.setSubSegments(subsegments);
        }
    }

    private String getAuthorizedPersonName() {
        return getFieldValue("Authorized Person Name");
    }

    private String getAuthorizedPersonMobile() {
        return getFieldValue("Authorized Person Phone Number");
    }

    private String getAuthorizedPersonEmail() {
        return getFieldValue("Authorized Person Email");
    }

    private String getFieldValue(String fieldName) {
        return FIELD_VALUE_BY_TITLE.apply(fieldName).scrollTo().getText();
    }
}
