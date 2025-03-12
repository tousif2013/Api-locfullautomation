package in.credable.automation.assertions.section;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.section.SectionVO;

public final class SectionAssertion extends CustomAssert<SectionAssertion, SectionVO> {
    SectionAssertion(SectionVO actual) {
        super(actual, SectionAssertion.class);
    }

    public SectionAssertion sectionIdIsNotNull() {
        isNotNull();
        super.propertyIsNotNull(actual.getId(), "sectionId");
        return this;
    }

    public SectionAssertion sectionNameIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getSectionName(), expected, "sectionName");
        return this;
    }

    public SectionAssertion sectionCodeIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getSectionCode(), expected, "sectionCode");
        return this;
    }

    public SectionAssertion sectionTypeIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getSectionType(), expected, "sectionType");
        return this;
    }

    public SectionAssertion isCustomSection() {
        isNotNull();
        super.failureWithActualExpectedForBooleanComparison(actual.getIsCustom(), Boolean.TRUE, "isCustom");
        return this;
    }

    public SectionAssertion isEditableSection() {
        isNotNull();
        super.failureWithActualExpectedForBooleanComparison(actual.getIsEditable(), Boolean.TRUE, "isEditable");
        return this;
    }

}
