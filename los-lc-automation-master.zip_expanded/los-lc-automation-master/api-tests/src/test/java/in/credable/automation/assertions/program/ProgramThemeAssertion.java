package in.credable.automation.assertions.program;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.program.ProgramThemeVO;

public final class ProgramThemeAssertion extends CustomAssert<ProgramThemeAssertion, ProgramThemeVO> {
    ProgramThemeAssertion(ProgramThemeVO actual) {
        super(actual, ProgramThemeAssertion.class);
    }

    public ProgramThemeAssertion hasSameProgramId(Long ProgramId) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getProgramId(), ProgramId, "Program Id");
        return this;
    }

    public ProgramThemeAssertion hasSameheaderSkinColor(String headerSkinColor) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getHeaderSkinColor(), headerSkinColor, "Header Skin Color");
        return this;
    }

    public ProgramThemeAssertion hasSamePrimaryButtonColor(String primaryButtonColor) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getPrimaryButtonColor(), primaryButtonColor, "Primary Button Color");
        return this;
    }

    public ProgramThemeAssertion hasSameSecondaryButtonColor(String secondaryButtonColor) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getSecondaryButtonColor(), secondaryButtonColor, "Secondary Button Color");
        return this;
    }

    public ProgramThemeAssertion hasSamefontName(String fontName) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getFontName(), fontName, "font Name");
        return this;
    }

    public ProgramThemeAssertion hasSamefooterSkinColor(String footerSkinColor) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getFooterSkinColor(), footerSkinColor, "Footer Skin Color");
        return this;
    }

    public ProgramThemeAssertion hasSameFooterText(String footerText) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getFooterText(), footerText, "Footer Text");
        return this;
    }

}