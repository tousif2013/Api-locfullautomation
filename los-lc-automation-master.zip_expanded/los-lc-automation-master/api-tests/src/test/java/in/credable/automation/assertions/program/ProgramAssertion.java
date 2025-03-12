package in.credable.automation.assertions.program;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.program.PGNGRuleVO;
import in.credable.automation.service.vo.program.ProgramStatus;
import in.credable.automation.service.vo.program.ProgramVO;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Objects;

public final class ProgramAssertion extends CustomAssert<ProgramAssertion, ProgramVO> {
    ProgramAssertion(ProgramVO actual) {
        super(actual, ProgramAssertion.class);
    }

    public ProgramAssertion programIdIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getId())) {
            failWithMessage("Program id should not be null.");
        }
        return this;
    }

    public ProgramAssertion hasSameProgramId(Long expectedProgramId) {
        isNotNull();
        failureWithActualExpectedForNumberComparison(actual.getId(), expectedProgramId, "Program id");
        return this;
    }

    public ProgramAssertion hasSameProgramCode(String expectedProgramCode) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getProgramCode(), expectedProgramCode, "Program Code");
        return this;
    }

    public ProgramAssertion hasSameProgramName(String expectedProgramName) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getProgramName(), expectedProgramName, "Program Name");
        return this;
    }

    public ProgramAssertion hasSameProductCategoryId(Long expectedProductCategoryId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getProductCategoryId(),
                expectedProductCategoryId,
                "Product Category Id");
        return this;
    }

    public ProgramAssertion hasSameProductTypeId(Long expectedProductTypeId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getProductTypeId(),
                expectedProductTypeId,
                "Product Type Id");
        return this;
    }

    public ProgramAssertion hasSameAnchorId(Long expectedAnchorId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getAnchorId(), expectedAnchorId, "Anchor Id");
        return this;
    }

    public ProgramAssertion hasSameCountryId(Long expectedCountryId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getCountryId(),
                expectedCountryId,
                "Country Id");
        return this;
    }

    public ProgramAssertion hasSameClientId(Long expectedClientId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getClientId(), expectedClientId, "Client Id");
        return this;
    }

    public ProgramAssertion hasSamePgngRules(List<PGNGRuleVO> expectedPgngRules) {
        isNotNull();
        Assertions.assertThat(this.actual.getPgngRules())
                .as(() -> "PGNG rules are not matching with expected")
                .hasSameSizeAs(expectedPgngRules)
                .hasSameElementsAs(expectedPgngRules);
        return this;
    }

    public ProgramAssertion hasSameMakerCheckerApprovalRequired(Boolean expectedMakerCheckerApprovalRequired) {
        isNotNull();
        if (!Objects.equals(this.actual.getMakerCheckerApprovalRequired(), expectedMakerCheckerApprovalRequired)) {
            throw failureWithActualExpected(this.actual.getMakerCheckerApprovalRequired(),
                    expectedMakerCheckerApprovalRequired,
                    "Expected makerCheckerApprovalRequired to be <%s> but found <%s>",
                    expectedMakerCheckerApprovalRequired,
                    this.actual.getMakerCheckerApprovalRequired());
        }
        return this;
    }

    public ProgramAssertion hasSameProgramStatus(ProgramStatus expectedProgramStatus) {
        isNotNull();
        if (!Objects.equals(this.actual.getProgramStatus(), expectedProgramStatus)) {
            throw failureWithActualExpected(this.actual.getProgramStatus(),
                    expectedProgramStatus,
                    "Expected programStatus to be <%s> but found <%s>",
                    expectedProgramStatus,
                    this.actual.getProgramStatus());
        }
        return this;
    }

    public ProgramAssertion hasSameRemark(String expectedRemark) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getRemark(), expectedRemark, "Remark");
        return this;
    }

    public ProgramAssertion hasSameCoolOffDays(Integer coolOffDays) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getCoolOffDays(), coolOffDays, "Cool Off Days");
        return this;
    }

    public ProgramAssertion hasSameGroupCode(String groupCode) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getGroupCode(), groupCode, "Group Code");
        return this;
    }

    public ProgramAssertion hasSameExpireDays(Integer expireDays) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getExpiryDays(), expireDays, "Expire Days");
        return this;
    }

    public ProgramAssertion hasSameChatBotServiceProviderId(Long ChatBotId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getChatBotServiceProviderId(), ChatBotId, "ChatBot Service Provider Id");
        return this;
    }

    public ProgramAssertion hasSameDefaultLanguageId(Long DefaultLanguageId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getDefaultLanguageId(), DefaultLanguageId, "Default Language Id");
        return this;
    }

    public ProgramAssertion hasSameEmailServiceProviderId(Long EmailServiceProviderId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getEmailServiceProviderId(), EmailServiceProviderId, "Email Service provider Id");
        return this;
    }

    public ProgramAssertion hasSameMarketingAutomationServiceProviderId(long MarketingAutomationServiceProvideId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getMarketingAutomationServiceProviderId(), MarketingAutomationServiceProvideId, "Marketing Automation Service ProvideId");
        return this;
    }

    public ProgramAssertion hasSameSMSServiceProviderId(Long smsServiceProviderId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getSmsServiceProviderId(), smsServiceProviderId, "SMS ServiceProvider Id");
        return this;
    }

    public ProgramAssertion hasSameLanguageIdList(List<Object> languageIds) {
        isNotNull();
        super.failureWithActualExpected(actual.getLanguageIds(), languageIds, "Different error Language id displaying.");
        return this;
    }

}