package in.credable.automation.assertions.section;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.section.SectionProgramMappingVO;
import in.credable.automation.service.vo.section.SectionVO;

public final class SectionProgramMappingAssertion extends CustomAssert<SectionProgramMappingAssertion, SectionProgramMappingVO> {
    SectionProgramMappingAssertion(SectionProgramMappingVO actual) {
        super(actual, SectionProgramMappingAssertion.class);
    }

    public SectionProgramMappingAssertion sectionProgramMappingIdIsNotNull() {
        isNotNull();
        super.propertyIsNotNull(actual.getId(), "sectionProgramMappingId");
        return this;
    }

    public SectionProgramMappingAssertion programIdIs(Long expected) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getProgramId(), expected, "programId");
        return this;
    }

    public SectionProgramMappingAssertion hasSameSection(SectionVO expected) {
        isNotNull();
        SectionAssertion sectionAssertion = new SectionAssertion(actual.getSection());
        sectionAssertion.isEqualTo(expected);
        return this;
    }

    public SectionProgramMappingAssertion sequenceNoIsPositiveValue() {
        isNotNull();
        if (actual.getSequenceNo() == null || actual.getSequenceNo() <= 0) {
            failWithMessage("SequenceNo is not positive");
        }
        return this;
    }

    public SectionProgramMappingAssertion isActive() {
        isNotNull();
        super.failureWithActualExpectedForBooleanComparison(actual.getIsActive(), Boolean.TRUE, "IsActive");
        return this;
    }

    public SectionProgramMappingAssertion isNotActive() {
        isNotNull();
        super.failureWithActualExpectedForBooleanComparison(actual.getIsActive(), Boolean.FALSE, "IsActive");
        return this;
    }

    public SectionProgramMappingAssertion sectionProgramMappingIdIs(Long expected) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getId(), expected, "sectionProgramMappingId");
        return this;
    }

    public SectionProgramMappingAssertion sequenceNoIs(Integer expected) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getSequenceNo(), expected, "sequenceNo");
        return this;
    }

    public SectionProgramMappingAssertion sectionJsonIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getSectionJson(), expected, "sectionJson");
        return this;
    }

}
