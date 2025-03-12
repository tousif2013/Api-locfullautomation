package in.credable.automation.ui.steplib.adminapp;

import in.credable.automation.service.vo.client.AnchorSubSegmentVO;
import in.credable.automation.service.vo.client.AnchorVO;
import in.credable.automation.service.vo.client.ApprovalStatusEnum;
import in.credable.automation.service.vo.client.MobileNumberVO;
import in.credable.automation.ui.assertions.FrameworkAssertions;
import in.credable.automation.ui.language.LabelKey;
import in.credable.automation.ui.pages.adminapp.corporate.CorporatePage;
import in.credable.automation.ui.pages.adminapp.corporate.CorporateTableColumn;
import in.credable.automation.ui.pages.modals.corporate.CorporateDetailsModal;
import in.credable.automation.ui.pages.modals.corporate.CreateCorporateModal;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.RandomDataGenerator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RegExUtils;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

@Log4j2
public class CorporatePageSteps {
    private final CorporatePage corporatePage;
    private AnchorVO anchorVO;
    private CreateCorporateModal createCorporateModal;
    private CorporateDetailsModal corporateDetailsModal;

    public CorporatePageSteps(CorporatePage corporatePage) {
        this.corporatePage = corporatePage;
    }

    public CorporatePageSteps verifyCorporatePageIsOpened() {
        Assertions.assertThat(this.corporatePage.isNavigatedToCorporatePage())
                .as(() -> "Corporate page is not opened")
                .isTrue();
        log.info("Navigated to corporate page.");
        return this;
    }

    public CorporatePageSteps openCreateCorporateModal() {
        createCorporateModal = this.corporatePage.openAddNewCorporateModal();
        Assertions.assertThat(createCorporateModal.isModalOpened())
                .as(() -> "Create corporate modal is not opened")
                .isTrue();
        FrameworkAssertions.assertThat(createCorporateModal.getModalTitle())
                .as(() -> "Create corporate modal title is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_TITLE_ADD);
        log.info("Opened create corporate modal");
        return this;
    }

    public CorporatePageSteps verifyCreateOrEditCorporateModalLabels() {
        FrameworkAssertions.assertThat(createCorporateModal.getCorporateProfileLabelValue())
                .as(() -> "Corporate header is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_PROFILE);
        FrameworkAssertions.assertThat(createCorporateModal.getCorporateNameInputPlaceholder())
                .as(() -> "Corporate name input placeholder is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_NAME);
        FrameworkAssertions.assertThat(createCorporateModal.getCorporateEmailInputPlaceholder())
                .as(() -> "Corporate email input placeholder is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_EMAIL);
        FrameworkAssertions.assertThat(createCorporateModal.getPANInputPlaceholder())
                .as(() -> "Corporate PAN input placeholder is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_PAN);
        FrameworkAssertions.assertThat(createCorporateModal.getGSTINInputPlaceholder())
                .as(() -> "Corporate GSTIN input placeholder is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_GSTIN);
        FrameworkAssertions.assertThat(createCorporateModal.getCorporateAddressLabelValue())
                .as(() -> "Corporate address header is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_ADDRESS);
        FrameworkAssertions.assertThat(createCorporateModal.getCorporateAddressLineInputPlaceholder())
                .as(() -> "Corporate address line 1 input placeholder is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_ADDRESS_LINE);
        FrameworkAssertions.assertThat(createCorporateModal.getCorporateCityInputPlaceholder())
                .as(() -> "Corporate city input placeholder is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_CITY);
        FrameworkAssertions.assertThat(createCorporateModal.getCorporateStateInputPlaceholder())
                .as(() -> "Corporate state input placeholder is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_STATE);
        FrameworkAssertions.assertThat(createCorporateModal.getCorporateCountryInputPlaceholder())
                .as(() -> "Corporate country input placeholder is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_COUNTRY);
        FrameworkAssertions.assertThat(createCorporateModal.getCorporatePinCodeInputPlaceholder())
                .as(() -> "Corporate pin code input placeholder is not correct")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_PIN_CODE);
        FrameworkAssertions.assertThat(createCorporateModal.getCorporateSubSegmentTitleLabelValue())
                .as(() -> "Corporate subsegment title label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_SUBSEGMENT);
        FrameworkAssertions.assertThat(createCorporateModal.getYesButtonLabelValue())
                .as(() -> "Yes button label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_YES);
        FrameworkAssertions.assertThat(createCorporateModal.getNoButtonLabelValue())
                .as(() -> "No button label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_NO);
        FrameworkAssertions.assertThat(createCorporateModal.getAuthorizedPersonDetailsLabelValue())
                .as(() -> "Authorized person details label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_PERSON_DETAILS);
        FrameworkAssertions.assertThat(createCorporateModal.getAuthorizedPersonNameInputPlaceholder())
                .as(() -> "Authorized person name input placeholder is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_PERSON_NAME);
        FrameworkAssertions.assertThat(createCorporateModal.getAuthorizedPersonPhoneInputPlaceholder())
                .as(() -> "Authorized person mobile input placeholder is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_PERSON_PHONE_NO);
        FrameworkAssertions.assertThat(createCorporateModal.getAuthorizedPersonEmailInputPlaceholder())
                .as(() -> "Authorized person email input placeholder is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_PERSON_EMAIL);
        log.info("Verified create or edit corporate modal labels");
        return this;
    }

    public CorporatePageSteps verifyMandatoryFieldsInCreateCorporateModal() {
        createCorporateModal.leaveAllFieldsEmpty();
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForCorporateName())
                .as(() -> "Corporate name is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_NAME);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForCorporateEmail())
                .as(() -> "Corporate email is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_EMAIL);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForPAN())
                .as(() -> "Corporate PAN is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_PAN);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForGSTIN())
                .as(() -> "Corporate GSTIN is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_GSTIN);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForCorporateAddress())
                .as(() -> "Corporate address line is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_ADDRESS_LINE);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForCorporateCity())
                .as(() -> "Corporate city is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_CITY);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForCorporateState())
                .as(() -> "Corporate state is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_STATE);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForCorporateCountry())
                .as(() -> "Corporate country is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_COUNTRY);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForCorporatePINCode())
                .as(() -> "Corporate pin code is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_PIN_CODE);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForAuthorizedPersonName())
                .as(() -> "Authorized person name is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_PERSON_NAME_ENTER);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForAuthorizedPersonPhone())
                .as(() -> "Authorized person mobile number is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_PHONE_NUMBER);
        FrameworkAssertions.assertThat(createCorporateModal.getInvalidFieldErrorMessageForAuthorizedPersonEmail())
                .as(() -> "Authorized person email is mandatory")
                .labelValueIsEqualTo(LabelKey.CORPORATE_CREATE_VALIDATION_EMAIL);
        Assertions.assertThat(createCorporateModal.isSubmitButtonDisabled())
                .as(() -> "Submit button is enabled")
                .isTrue();
        log.info("Verified mandatory fields in create corporate modal");
        return this;
    }

    public CorporatePageSteps createNewCorporateWithMandatoryDetails() {
        // Generate random details to create corporate.
        anchorVO = DataProviderUtil.manufacturePojo(AnchorVO.class);
        anchorVO.setAnchorName(RegExUtils.removeAll(anchorVO.getAnchorName(), "-"));
        String mobileNumber = RandomDataGenerator.generateRandomMobileNumber();
        anchorVO.setAuthorizedPersonMobileNumber(new MobileNumberVO("+91", mobileNumber));
        createCorporateModal.enterCorporateDetailsAndCreate(anchorVO);
        log.info("Created new corporate with mandatory details");
        return this;
    }

    public CorporatePageSteps createNewCorporateWithAllDetails() {
        // Generate random details to create corporate.
        anchorVO = DataProviderUtil.manufacturePojo(AnchorVO.class);
        anchorVO.setAnchorName(RegExUtils.removeAll(anchorVO.getAnchorName(), "-"));
        String mobileNumber = RandomDataGenerator.generateRandomMobileNumber();
        anchorVO.setAuthorizedPersonMobileNumber(new MobileNumberVO("+91", mobileNumber));
        AnchorSubSegmentVO anchorSubSegmentVO = DataProviderUtil.manufacturePojo(AnchorSubSegmentVO.class);
        anchorVO.setSubSegments(List.of(anchorSubSegmentVO));
        createCorporateModal.enterCorporateDetailsAndCreate(anchorVO);
        anchorVO.setApprovalStatus(ApprovalStatusEnum.PENDING);
        log.info("Created new corporate with all details");
        return this;
    }

    public CorporatePageSteps verifyCorporateDetailsAreDisplayedInTheFirstRow() {
        AnchorVO corporateDetailsOfFirstRow = this.corporatePage.getCorporateDetailsOfFirstRow();
        Long anchorId = corporateDetailsOfFirstRow.getId();
        Assertions.assertThat(anchorId)
                .as(() -> "Corporate id is not generated")
                .isNotNull();
        anchorVO.setId(anchorId);
        Assertions.assertThat(corporateDetailsOfFirstRow.getAnchorName())
                .as(() -> "Corporate name is not as per input")
                .isEqualTo(anchorVO.getAnchorName());
        if (CollectionUtils.isEmpty(anchorVO.getSubSegments())) {
            Assertions.assertThat(corporateDetailsOfFirstRow.getSubSegments())
                    .as(() -> "Subsegment name is not empty")
                    .isNull();
        } else {
            Assertions.assertThat(corporateDetailsOfFirstRow.getSubSegments().stream()
                            .map(AnchorSubSegmentVO::getSubSegmentName))
                    .as(() -> "Subsegment name is not as per input")
                    .containsExactlyInAnyOrderElementsOf(anchorVO.getSubSegments().stream()
                            .map(AnchorSubSegmentVO::getSubSegmentName)
                            .toList());
        }

        Assertions.assertThat(corporateDetailsOfFirstRow.getAuthorizedPersonName())
                .as(() -> "Corporate authorized person name is not as per input")
                .isEqualTo(anchorVO.getAuthorizedPersonName());
        Assertions.assertThat(corporateDetailsOfFirstRow.getAuthorizedPersonMobileNumber())
                .as(() -> "Corporate authorized person mobile number is not as per input")
                .isEqualTo(anchorVO.getAuthorizedPersonMobileNumber());
        Assertions.assertThat(corporateDetailsOfFirstRow.getApprovalStatus())
                .as(() -> "Corporate status is not as per input")
                .isEqualTo(anchorVO.getApprovalStatus());

        log.info("Newly created corporate is visible in the first row of corporate table");
        return this;
    }

    public CorporatePageSteps openCorporateDetailsModalForCreatedCorporate() {
        corporateDetailsModal = this.corporatePage.openCorporateDetailsModalOfFirstRow();
        Assertions.assertThat(corporateDetailsModal.isModalOpened())
                .as(() -> "Corporate details modal is not opened")
                .isTrue();
        log.info("Opened corporate details modal");
        return this;
    }

    public CorporatePageSteps verifyCorporateDetailsModalLabels() {
        FrameworkAssertions.assertThat(corporateDetailsModal.getApproveButtonText())
                .as(() -> "Approve button text is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_APPROVE);

        FrameworkAssertions.assertThat(corporateDetailsModal.getRejectButtonText())
                .as(() -> "Reject button text is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_REJECT);

        FrameworkAssertions.assertThat(corporateDetailsModal.getCorporateProfileLabelValue())
                .as(() -> "Corporate profile label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_CORPORATE_PROFILE);

        FrameworkAssertions.assertThat(corporateDetailsModal.getCorporateNameLabelValue())
                .as(() -> "Corporate name label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_CORPORATE_NAME);

        FrameworkAssertions.assertThat(corporateDetailsModal.getCorporateEmailLabelValue())
                .as(() -> "Corporate email label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_CORPORATE_EMAIL);

        FrameworkAssertions.assertThat(corporateDetailsModal.getCorporatePANLabelValue())
                .as(() -> "Corporate PAN label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_PAN);

        FrameworkAssertions.assertThat(corporateDetailsModal.getCorporateGSTLabelValue())
                .as(() -> "Corporate GSTIN label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_GSTIN);

        FrameworkAssertions.assertThat(corporateDetailsModal.getCorporateAddressLabelValue())
                .as(() -> "Corporate address label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_CORPORATE_ADDRESS);

        FrameworkAssertions.assertThat(corporateDetailsModal.getCorporateSubSegmentLabelValue())
                .as(() -> "Corporate sub segment label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_SUB_SEGMENT_DETAIL);

        FrameworkAssertions.assertThat(corporateDetailsModal.getAuthorizedPersonDetailsLabelValue())
                .as(() -> "Authorized person details label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_PERSON_DETAIL);

        FrameworkAssertions.assertThat(corporateDetailsModal.getAuthorizedPersonNameInputPlaceholder())
                .as(() -> "Authorized person name label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_PERSON_NAME);

        FrameworkAssertions.assertThat(corporateDetailsModal.getAuthorizedPersonPhoneInputPlaceholder())
                .as(() -> "Authorized person phone label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_PERSON_NUMBER);

        FrameworkAssertions.assertThat(corporateDetailsModal.getAuthorizedPersonEmailInputPlaceholder())
                .as(() -> "Authorized person email label value is not matching")
                .labelValueIsEqualTo(LabelKey.CORPORATE_VIEW_PERSON_EMAIL);

        return this;
    }

    public CorporatePageSteps verifyCorporateDetailsModalDetails() {
        AnchorVO corporateDetails = corporateDetailsModal.getCorporateDetails();
        Assertions.assertThat(corporateDetails.getId())
                .as(() -> "Corporate is not matching")
                .isEqualTo(anchorVO.getId());
        Assertions.assertThat(corporateDetails.getAnchorName())
                .as(() -> "Corporate name is not as per input")
                .isEqualTo(anchorVO.getAnchorName());
        Assertions.assertThat(corporateDetails.getAnchorEmail())
                .as(() -> "Corporate email is not as per input")
                .isEqualTo(anchorVO.getAnchorEmail());
        Assertions.assertThat(corporateDetails.getBusinessPAN())
                .as(() -> "Corporate business pan is not as per input")
                .isEqualTo(anchorVO.getBusinessPAN());
        Assertions.assertThat(corporateDetails.getGstin())
                .as(() -> "Corporate gstin is not as per input")
                .isEqualTo(anchorVO.getGstin());
        Assertions.assertThat(corporateDetails.getAnchorAddress())
                .as(() -> "Corporate address is not as per input")
                .contains(anchorVO.getAnchorAddress());
        Assertions.assertThat(corporateDetails.getAnchorAddress())
                .as(() -> "Corporate city is not as per input")
                .contains(anchorVO.getAnchorCity());
        Assertions.assertThat(corporateDetails.getAnchorAddress())
                .as(() -> "Corporate state is not as per input")
                .contains(anchorVO.getAnchorState());
        Assertions.assertThat(corporateDetails.getAnchorAddress())
                .as(() -> "Corporate country is not as per input")
                .contains(anchorVO.getAnchorCountry());
        Assertions.assertThat(corporateDetails.getAnchorAddress())
                .as(() -> "Corporate pincode is not as per input")
                .contains(anchorVO.getAnchorPinCode());
        Assertions.assertThat(corporateDetails.getAuthorizedPersonName())
                .as(() -> "Corporate authorized person name is not as per input")
                .isEqualTo(anchorVO.getAuthorizedPersonName());
        Assertions.assertThat(corporateDetails.getAuthorizedPersonMobileNumber())
                .as(() -> "Corporate authorized person mobile number is not as per input")
                .isEqualTo(anchorVO.getAuthorizedPersonMobileNumber());
        Assertions.assertThat(corporateDetails.getAuthorizedPersonEmail())
                .as(() -> "Corporate authorized person email is not as per input")
                .isEqualTo(anchorVO.getAuthorizedPersonEmail());
        return this;
    }

    public CorporatePageSteps approveCorporate() {
        corporateDetailsModal.approveCorporate();
        anchorVO.setApprovalStatus(ApprovalStatusEnum.APPROVED);
        log.info("Approved corporate");
        return this;
    }

    public CorporatePageSteps rejectCorporate() {
        corporateDetailsModal.rejectCorporate();
        anchorVO.setApprovalStatus(ApprovalStatusEnum.REJECTED);
        log.info("Rejected corporate");
        return this;
    }

    public CorporatePageSteps closeCorporateDetailsModal() {
        corporateDetailsModal.closeModal();
        log.info("Closed corporate details modal");
        return this;
    }

    public CorporatePageSteps verifyCorporateIsApproved() {
        Assertions.assertThat(this.corporatePage.getCorporateDetailsOfFirstRow().getApprovalStatus())
                .as(() -> "Corporate is not approved")
                .isEqualTo(ApprovalStatusEnum.APPROVED);
        return this;
    }

    public CorporatePageSteps openEditCorporateDetailsModalForCreatedCorporate() {
        createCorporateModal = this.corporatePage.openEditCorporateDetailsModalOfFirstRow();
        Assertions.assertThat(createCorporateModal.isModalOpened())
                .as(() -> "Corporate details modal is not opened")
                .isTrue();
        log.info("Opened edit corporate details modal");
        return this;
    }

    public CorporatePageSteps verifyAllFieldsAreDisabledExceptSubsegment() {
        Assertions.assertThat(createCorporateModal.isAnchorNameInputEditable())
                .as(() -> "Anchor name is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isAnchorEmailInputEditable())
                .as(() -> "Anchor email is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isAnchorPANInputEditable())
                .as(() -> "Business PAN is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isGSTINInputEditable())
                .as(() -> "GSTIN is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isAnchorAddressInputEditable())
                .as(() -> "Anchor address is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isAnchorCityInputEditable())
                .as(() -> "Anchor city is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isAnchorStateInputEditable())
                .as(() -> "Anchor state is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isAnchorCountryInputEditable())
                .as(() -> "Anchor country is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isAnchorPINCodeInputEditable())
                .as(() -> "Anchor pincode is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isAuthorizedPersonNameInputEditable())
                .as(() -> "Authorized person name is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isAuthorizedPersonPhoneInputEditable())
                .as(() -> "Authorized person mobile number is editable")
                .isFalse();
        Assertions.assertThat(createCorporateModal.isAuthorizedPersonEmailInputEditable())
                .as(() -> "Authorized person email is editable")
                .isFalse();
        return this;
    }

    public CorporatePageSteps addSubSegment() {
        AnchorSubSegmentVO anchorSubSegmentVO = DataProviderUtil.manufacturePojo(AnchorSubSegmentVO.class);
        createCorporateModal.addSubSegment(anchorSubSegmentVO);
        createCorporateModal.submitCorporateDetails();
        this.anchorVO.setSubSegments(List.of(anchorSubSegmentVO));
        log.info("Added subsegment");
        return this;
    }

    public CorporatePageSteps updateCorporateWithMandatoryDetails() {
        // Generate random details to update corporate.
        anchorVO = DataProviderUtil.manufacturePojo(AnchorVO.class);
        anchorVO.setAnchorName(RegExUtils.removeAll(anchorVO.getAnchorName(), "-"));
        String mobileNumber = RandomDataGenerator.generateRandomMobileNumber();
        MobileNumberVO mobileNumberVO = new MobileNumberVO("+91", mobileNumber);
        anchorVO.setAuthorizedPersonMobileNumber(mobileNumberVO);
        AnchorSubSegmentVO anchorSubSegmentVO = DataProviderUtil.manufacturePojo(AnchorSubSegmentVO.class);
        anchorVO.setSubSegments(List.of(anchorSubSegmentVO));
        createCorporateModal.enterCorporateDetailsAndCreate(anchorVO);
        log.info("Updated corporate with mandatory details");
        return this;
    }

    public CorporatePageSteps verifyCorporateIsRejected() {
        Assertions.assertThat(this.corporatePage.getCorporateDetailsOfFirstRow().getApprovalStatus())
                .as(() -> "Corporate is not rejected")
                .isEqualTo(ApprovalStatusEnum.REJECTED);
        log.info("Verified corporate is rejected");
        return this;
    }

    public void verifyRejectedCorporateIsNotEditable() {
        Assertions.assertThat(this.corporatePage.isEditLinkDisabledOfFirstRow())
                .as(() -> "Edit link is enabled")
                .isTrue();
        log.info("Verified rejected corporate is not editable");
    }

    public CorporatePageSteps verifyLanguageConversionOnCorporateDashboardPage() {
        FrameworkAssertions.assertThat(corporatePage.getPageTitleText())
                .as(() -> "Page title text is not expected")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_CORPORATE);

        FrameworkAssertions.assertThat(corporatePage.getCreateCorporateButtonText())
                .as(() -> "Create corporate button text is not expected")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_NEW_CORPORATE);

        FrameworkAssertions.assertThat(corporatePage.getSearchCorporateInputPlaceholder())
                .as(() -> "Search corporate input placeholder is not expected")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_SEARCH);

        FrameworkAssertions.assertThat(corporatePage.getPaginationFirstButtonText())
                .as(() -> "Pagination first button text is not expected")
                .labelValueIsEqualTo(LabelKey.PAGINATION_FIRST);

        FrameworkAssertions.assertThat(corporatePage.getPaginationLastButtonText())
                .as(() -> "Pagination last button text is not expected")
                .labelValueIsEqualTo(LabelKey.PAGINATION_LAST);

        Map<CorporateTableColumn, String> corporateTableHeaderMap = corporatePage.getCorporateTableHeader();

        FrameworkAssertions.assertThat(corporateTableHeaderMap.get(CorporateTableColumn.CORPORATE_ID))
                .as(() -> "Corporate id label value is not expected")
                .labelValueIsEqualToIgnoringCase(LabelKey.CORPORATE_VIEW_HEADER_ID);

        FrameworkAssertions.assertThat(corporateTableHeaderMap.get(CorporateTableColumn.CORPORATE_NAME))
                .as(() -> "Corporate name label value is not expected")
                .labelValueIsEqualToIgnoringCase(LabelKey.CORPORATE_VIEW_HEADER_NAME);

        FrameworkAssertions.assertThat(corporateTableHeaderMap.get(CorporateTableColumn.CORPORATE_SUBSEGMENT))
                .as(() -> "Corporate subsegment label value is not expected")
                .labelValueIsEqualToIgnoringCase(LabelKey.CORPORATE_VIEW_HEADER_SUBSEGMENT);

        FrameworkAssertions.assertThat(corporateTableHeaderMap.get(CorporateTableColumn.AUTHORISED_PERSON_NAME))
                .as(() -> "Authorized person name label value is not expected")
                .labelValueIsEqualToIgnoringCase(LabelKey.CORPORATE_VIEW_HEADER_AUTHORISED_NAME);

        FrameworkAssertions.assertThat(corporateTableHeaderMap.get(CorporateTableColumn.AUTHORISED_PHONE_NUMBER))
                .as(() -> "Authorized person phone number label value is not expected")
                .labelValueIsEqualToIgnoringCase(LabelKey.CORPORATE_VIEW_HEADER_PHONE_NUMBER);

        FrameworkAssertions.assertThat(corporateTableHeaderMap.get(CorporateTableColumn.APPROVAL_STATUS))
                .as(() -> "Approval status label value is not expected")
                .labelValueIsEqualToIgnoringCase(LabelKey.CORPORATE_VIEW_HEADER_STATUS);

        FrameworkAssertions.assertThat(corporateTableHeaderMap.get(CorporateTableColumn.ACTION))
                .as(() -> "Action label value is not expected")
                .labelValueIsEqualToIgnoringCase(LabelKey.CORPORATE_VIEW_HEADER_ACTION);
        log.info("Verified language conversion on corporate dashboard page");
        return this;
    }

    public void closeEditCorporateDetailsModal() {
        createCorporateModal.closeModal();
        log.info("Closed edit corporate details modal");
    }
}
