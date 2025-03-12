package in.credable.automation.assertions.program;

import in.credable.automation.service.vo.program.ProgramThemeVO;
import in.credable.automation.service.vo.program.ProgramVO;

public final class ProgramAssertionsFactory {
    private ProgramAssertionsFactory() {
    }

    public static ProgramThemeAssertion createProgramThemeAssertion(ProgramThemeVO actual) {
        return new ProgramThemeAssertion(actual);
    }

    public static ProgramAssertion createProgramAssertion(ProgramVO actual) {
        return new ProgramAssertion(actual);
    }

}
