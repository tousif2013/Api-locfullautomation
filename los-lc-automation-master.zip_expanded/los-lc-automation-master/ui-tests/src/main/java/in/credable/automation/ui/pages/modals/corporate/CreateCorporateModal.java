package in.credable.automation.ui.pages.modals.corporate;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import in.credable.automation.service.vo.client.AnchorSubSegmentVO;
import in.credable.automation.service.vo.client.AnchorVO;
import in.credable.automation.ui.pages.modals.BaseModal;
import in.credable.automation.ui.utils.SelenideUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.Keys;

import java.util.function.UnaryOperator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreateCorporateModal extends BaseModal {
    private static final ElementsCollection CORPORATE_DETAIL_HEADERS = $$(byCssSelector("div.corporate_detail_header"));
    private static final SelenideElement CORPORATE_NAME_INPUT = $(byId("anchorName"));
    private static final SelenideElement CORPORATE_EMAIL_INPUT = $(byId("anchorEmailField"));
    private static final SelenideElement PAN_INPUT = $(byId("businessPANField"));
    private static final SelenideElement VERIFY_PAN_BUTTON = $(byCssSelector("div.verify_button"));
    private static final SelenideElement VERIFIED_PAN_BUTTON = $(byCssSelector("div.verified_button"));
    private static final SelenideElement GSTIN_DROPDOWN = $(byId("gstin"));
    private static final SelenideElement ANCHOR_ADDRESS_INPUT = $(byId("anchorAddress"));
    private static final SelenideElement ANCHOR_CITY_INPUT = $(byId("anchorCity"));
    private static final SelenideElement ANCHOR_STATE_INPUT = $(byId("anchorState"));
    private static final SelenideElement ANCHOR_COUNTRY_INPUT = $(byId("anchorCountry"));
    private static final SelenideElement ANCHOR_PINCODE_INPUT = $(byId("anchorPinCode"));
    private static final SelenideElement CORPORATE_SUB_SEGMENT_TITLE = $(byCssSelector("div.subsegment__title"));
    private static final SelenideElement ADD_SUBSEGMENT_RADIO_BUTTON = $(byCssSelector("input[type='radio'][id='elmYes']"));
    private static final SelenideElement ADD_SUBSEGMENT_RADIO_BUTTON_NO = $(byCssSelector("input[type='radio'][id='elmNo']"));
    private static final SelenideElement SUBSEGMENT_NAME_INPUT = $(byId("subsegment_name_"));
    private static final SelenideElement SUBSEGMENT_CODE_INPUT = $(byId("subsegment_code_"));
    private static final SelenideElement AUTHORIZED_PERSON_NAME_INPUT = $(byId("authorizedPersonName"));
    private static final SelenideElement AUTHORIZED_PERSON_PHONE_INPUT = $(byId("authorizedPersonMobileNumberField"));
    private static final SelenideElement AUTHORIZED_PERSON_EMAIL_INPUT = $(byId("authorizedPersonEmail"));
    private static final SelenideElement SUBMIT_BUTTON = $(byCssSelector("div.submit_button"));
    private static final String INVALID_FIELD_XPATH = ".//div[contains(@class,'invalid-field')]/div";
    private static final UnaryOperator<SelenideElement> GET_INVALID_FIELD = field ->
            field.ancestor(".input__field__container__box").find(byXpath(INVALID_FIELD_XPATH));


    public String getCorporateProfileLabelValue() {
        return CORPORATE_DETAIL_HEADERS.shouldBe(CollectionCondition.sizeGreaterThan(0)).first().getText();
    }

    public String getCorporateNameInputPlaceholder() {
        return CORPORATE_NAME_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public String getCorporateEmailInputPlaceholder() {
        return CORPORATE_EMAIL_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public String getPANInputPlaceholder() {
        return PAN_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public String getGSTINInputPlaceholder() {
        return GSTIN_DROPDOWN.shouldBe(visible).sibling(0).getText();
    }

    public String getCorporateAddressLabelValue() {
        return CORPORATE_DETAIL_HEADERS.get(1).getText();
    }

    public String getCorporateAddressLineInputPlaceholder() {
        return ANCHOR_ADDRESS_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public String getCorporateCityInputPlaceholder() {
        return ANCHOR_CITY_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public String getCorporateStateInputPlaceholder() {
        return ANCHOR_STATE_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public String getCorporateCountryInputPlaceholder() {
        return ANCHOR_COUNTRY_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public String getCorporatePinCodeInputPlaceholder() {
        return ANCHOR_PINCODE_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public String getCorporateSubSegmentTitleLabelValue() {
        return CORPORATE_SUB_SEGMENT_TITLE.shouldBe(visible).getText();
    }

    public String getYesButtonLabelValue() {
        return ADD_SUBSEGMENT_RADIO_BUTTON.shouldBe(visible).sibling(0).getText();
    }

    public String getNoButtonLabelValue() {
        return ADD_SUBSEGMENT_RADIO_BUTTON_NO.shouldBe(visible).sibling(0).getText();
    }

    public String getAuthorizedPersonDetailsLabelValue() {
        return CORPORATE_DETAIL_HEADERS.get(2).getText();
    }

    public String getAuthorizedPersonNameInputPlaceholder() {
        return AUTHORIZED_PERSON_NAME_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public String getAuthorizedPersonPhoneInputPlaceholder() {
        return AUTHORIZED_PERSON_PHONE_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public String getAuthorizedPersonEmailInputPlaceholder() {
        return AUTHORIZED_PERSON_EMAIL_INPUT.shouldBe(visible).sibling(0).getText();
    }

    public void leaveAllFieldsEmpty() {
        CORPORATE_NAME_INPUT.sendKeys(Keys.TAB);
        CORPORATE_EMAIL_INPUT.sendKeys(Keys.TAB);
        PAN_INPUT.sendKeys(Keys.TAB);
        GSTIN_DROPDOWN.sendKeys(Keys.TAB);
        ANCHOR_ADDRESS_INPUT.sendKeys(Keys.TAB);
        ANCHOR_CITY_INPUT.sendKeys(Keys.TAB);
        ANCHOR_STATE_INPUT.sendKeys(Keys.TAB);
        ANCHOR_COUNTRY_INPUT.sendKeys(Keys.TAB);
        ANCHOR_PINCODE_INPUT.sendKeys(Keys.TAB);
        AUTHORIZED_PERSON_NAME_INPUT.sendKeys(Keys.TAB);
        AUTHORIZED_PERSON_PHONE_INPUT.sendKeys(Keys.TAB);
        AUTHORIZED_PERSON_EMAIL_INPUT.sendKeys(Keys.TAB);
    }

    public String getInvalidFieldErrorMessageForCorporateName() {
        return GET_INVALID_FIELD.apply(CORPORATE_NAME_INPUT).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForCorporateEmail() {
        return GET_INVALID_FIELD.apply(CORPORATE_EMAIL_INPUT).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForPAN() {
        return GET_INVALID_FIELD.apply(PAN_INPUT).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForGSTIN() {
        return GET_INVALID_FIELD.apply(GSTIN_DROPDOWN).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForCorporateAddress() {
        return GET_INVALID_FIELD.apply(ANCHOR_ADDRESS_INPUT).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForCorporateCity() {
        return GET_INVALID_FIELD.apply(ANCHOR_CITY_INPUT).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForCorporateState() {
        return GET_INVALID_FIELD.apply(ANCHOR_STATE_INPUT).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForCorporateCountry() {
        return GET_INVALID_FIELD.apply(ANCHOR_COUNTRY_INPUT).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForCorporatePINCode() {
        return GET_INVALID_FIELD.apply(ANCHOR_PINCODE_INPUT).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForAuthorizedPersonName() {
        return GET_INVALID_FIELD.apply(AUTHORIZED_PERSON_NAME_INPUT).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForAuthorizedPersonPhone() {
        return GET_INVALID_FIELD.apply(AUTHORIZED_PERSON_PHONE_INPUT).scrollTo().getText();
    }

    public String getInvalidFieldErrorMessageForAuthorizedPersonEmail() {
        return GET_INVALID_FIELD.apply(AUTHORIZED_PERSON_EMAIL_INPUT).scrollTo().getText();
    }

    public boolean isSubmitButtonDisabled() {
        return SelenideUtils.isElementDisabled(SUBMIT_BUTTON);
    }

    public void enterCorporateDetailsAndCreate(AnchorVO anchorVO) {
        enterCorporateProfileDetails(anchorVO);
        enterAnchorAddressDetails(anchorVO);
        if (CollectionUtils.isNotEmpty(anchorVO.getSubSegments()) && anchorVO.getSubSegments().getFirst() != null) {
            addSubSegment(anchorVO.getSubSegments().getFirst());
        }
        enterAuthorizedPersonDetails(anchorVO);
        submitCorporateDetails();
    }

    public void addSubSegment(AnchorSubSegmentVO anchorSubSegmentVO) {
        ADD_SUBSEGMENT_RADIO_BUTTON.scrollTo().click();
        SUBSEGMENT_NAME_INPUT.shouldBe(visible).setValue(anchorSubSegmentVO.getSubSegmentName());
        SUBSEGMENT_CODE_INPUT.setValue(anchorSubSegmentVO.getSubSegmentCode());
    }

    public void submitCorporateDetails() {
        SUBMIT_BUTTON.shouldBe(enabled).click();
        POPUP_CONTAINER.shouldBe(not(visible));
    }

    public boolean isAnchorNameInputEditable() {
        return CORPORATE_NAME_INPUT.is(editable);
    }

    public boolean isAnchorEmailInputEditable() {
        return CORPORATE_EMAIL_INPUT.is(editable);
    }

    public boolean isAnchorPANInputEditable() {
        return PAN_INPUT.is(editable);
    }

    public boolean isGSTINInputEditable() {
        return GSTIN_DROPDOWN.is(editable);
    }

    public boolean isAnchorAddressInputEditable() {
        return ANCHOR_ADDRESS_INPUT.is(editable);
    }

    public boolean isAnchorCityInputEditable() {
        return ANCHOR_CITY_INPUT.is(editable);
    }

    public boolean isAnchorStateInputEditable() {
        return ANCHOR_STATE_INPUT.is(editable);
    }

    public boolean isAnchorCountryInputEditable() {
        return ANCHOR_COUNTRY_INPUT.is(editable);
    }

    public boolean isAnchorPINCodeInputEditable() {
        return ANCHOR_PINCODE_INPUT.is(editable);
    }

    public boolean isAuthorizedPersonNameInputEditable() {
        return AUTHORIZED_PERSON_NAME_INPUT.is(editable);
    }

    public boolean isAuthorizedPersonPhoneInputEditable() {
        return AUTHORIZED_PERSON_PHONE_INPUT.is(editable);
    }

    public boolean isAuthorizedPersonEmailInputEditable() {
        return AUTHORIZED_PERSON_EMAIL_INPUT.is(editable);
    }

    private void enterCorporateProfileDetails(AnchorVO anchorVO) {
        CORPORATE_NAME_INPUT.setValue(anchorVO.getAnchorName());
        CORPORATE_EMAIL_INPUT.setValue(anchorVO.getAnchorEmail());
        PAN_INPUT.setValue(anchorVO.getBusinessPAN());
        VERIFY_PAN_BUTTON.shouldBe(enabled).click();
        VERIFIED_PAN_BUTTON.shouldBe(visible);
        GSTIN_DROPDOWN.selectOption(anchorVO.getGstin());
    }

    private void enterAnchorAddressDetails(AnchorVO anchorVO) {
        ANCHOR_ADDRESS_INPUT.setValue(anchorVO.getAnchorAddress());
        ANCHOR_CITY_INPUT.setValue(anchorVO.getAnchorCity());
        ANCHOR_STATE_INPUT.setValue(anchorVO.getAnchorState());
        ANCHOR_COUNTRY_INPUT.setValue(anchorVO.getAnchorCountry());
        ANCHOR_PINCODE_INPUT.setValue(anchorVO.getAnchorPinCode());
    }

    private void enterAuthorizedPersonDetails(AnchorVO anchorVO) {
        AUTHORIZED_PERSON_NAME_INPUT.scrollTo().setValue(anchorVO.getAuthorizedPersonName());
        AUTHORIZED_PERSON_PHONE_INPUT.setValue(anchorVO.getAuthorizedPersonMobileNumber().getNumber());
        AUTHORIZED_PERSON_EMAIL_INPUT.setValue(anchorVO.getAuthorizedPersonEmail());
    }
}
