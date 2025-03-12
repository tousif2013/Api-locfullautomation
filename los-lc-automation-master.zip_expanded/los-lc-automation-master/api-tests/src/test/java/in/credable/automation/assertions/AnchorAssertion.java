package in.credable.automation.assertions;

import in.credable.automation.service.vo.client.AnchorVO;
import in.credable.automation.service.vo.client.ApprovalStatusEnum;
import in.credable.automation.service.vo.client.MobileNumberVO;

import java.util.Objects;

public final class AnchorAssertion extends CustomAssert<AnchorAssertion, AnchorVO> {
    AnchorAssertion(AnchorVO actual) {
        super(actual, AnchorAssertion.class);
    }

    public AnchorAssertion anchorIdIsNotNull() {
        isNotNull();

        if (Objects.isNull(actual.getId())) {
            failWithMessage("Anchor id should not be null.");
        }

        return this;
    }

    public AnchorAssertion hasSameClientId(Long expectedClientId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getClientId(), expectedClientId, "Client Id");
        return this;
    }

    public AnchorAssertion hasSameAnchorCode(String expectedAnchorCode) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAnchorCode(), expectedAnchorCode, "Anchor Code");
        return this;
    }

    public AnchorAssertion hasSameAnchorName(String expectedAnchorName) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAnchorName(), expectedAnchorName, "Anchor Name");
        return this;
    }

    public AnchorAssertion hasSameAnchorEmail(String expectedAnchorEmail) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAnchorEmail(), expectedAnchorEmail, "Anchor Email");
        return this;
    }

    public AnchorAssertion hasSameAnchorPAN(String expectedBusinessPAN) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getBusinessPAN(), expectedBusinessPAN, "Business PAN");
        return this;
    }

    public AnchorAssertion hasSameAnchorGSTIN(String expectedGSTIN) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getGstin(), expectedGSTIN, "GSTIN");
        return this;
    }

    public AnchorAssertion hasSameAnchorAddress(String expectedAddress) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAnchorAddress(), expectedAddress, "Anchor Address");
        return this;
    }

    public AnchorAssertion hasSameAnchorCity(String expectedCity) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAnchorCity(), expectedCity, "Anchor City");
        return this;
    }

    public AnchorAssertion hasSameAnchorState(String expectedState) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAnchorState(), expectedState, "Anchor State");
        return this;
    }

    public AnchorAssertion hasSameAnchorCountry(String expectedCountry) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAnchorCountry(), expectedCountry, "Anchor Country");
        return this;
    }

    public AnchorAssertion hasSameAnchorPinCode(String expectedPINCode) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAnchorPinCode(), expectedPINCode, "Anchor PINCode");
        return this;
    }

    public AnchorAssertion hasSameAuthorizedPersonName(String authorizedPersonName) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAuthorizedPersonName(), authorizedPersonName, "Authorized Person Name");
        return this;
    }

    public AnchorAssertion hasSameAuthorizedPersonEmail(String authorizedPersonEmail) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAuthorizedPersonEmail(), authorizedPersonEmail, "Authorized Person Name");
        return this;
    }

    public AnchorAssertion hasSameAuthorizedPersonMobileNo(MobileNumberVO authorizedPersonMobileNo) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getAuthorizedPersonMobileNumber().getNumberWithDialingCode(),
                authorizedPersonMobileNo.getNumberWithDialingCode(), "Authorized Person Mobile Number");
        return this;
    }

    public AnchorAssertion hasSameSubsegmentCode(String subSegmentCode) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getSubSegments().getFirst().getSubSegmentCode(), subSegmentCode, "SubSegment Code");
        return this;
    }

    public AnchorAssertion hasSameSubsegmentName(String subSegmentName) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getSubSegments().getFirst().getSubSegmentName(), subSegmentName, "SubSegment Name");
        return this;
    }

    public AnchorAssertion hasSameApprovalStatus(ApprovalStatusEnum expected) {
        isNotNull();
        failureWithActualExpectedForEnumComparison(actual.getApprovalStatus(), expected, "Approval Status");
        return this;
    }
}
