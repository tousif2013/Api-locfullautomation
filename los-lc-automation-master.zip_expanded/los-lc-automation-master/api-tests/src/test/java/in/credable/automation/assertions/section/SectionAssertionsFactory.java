package in.credable.automation.assertions.section;

import in.credable.automation.service.vo.section.SectionProgramMappingVO;
import in.credable.automation.service.vo.section.SectionVO;

public final class SectionAssertionsFactory {
    private SectionAssertionsFactory() {
    }

    /**
     * @param actual The {@link SectionProgramMappingVO} object to be asserted on.
     * @return Instance of {@link SectionProgramMappingAssertion} class.
     */
    public static SectionProgramMappingAssertion createSectionProgramMappingAssertion(SectionProgramMappingVO actual) {
        return new SectionProgramMappingAssertion(actual);
    }

    /**
     * Creates instance of {@link SectionAssertion} class
     *
     * @param actual The {@link SectionVO} object to be asserted on.
     * @return Instance of {@link SectionAssertion} class.
     */
    public static SectionAssertion createSectionAssertion(SectionVO actual) {
        return new SectionAssertion(actual);
    }
}
