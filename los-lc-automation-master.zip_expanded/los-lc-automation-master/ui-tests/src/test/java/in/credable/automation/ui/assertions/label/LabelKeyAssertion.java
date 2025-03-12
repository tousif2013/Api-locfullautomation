package in.credable.automation.ui.assertions.label;

import in.credable.automation.ui.language.Translator;
import org.assertj.core.api.Assertions;

import java.util.function.Supplier;

public final class LabelKeyAssertion {
    private final String actual;
    private Supplier<String> descriptionSupplier;

    public LabelKeyAssertion(String actual) {
        this.actual = actual;
    }

    public LabelKeyAssertion as(Supplier<String> description) {
        this.descriptionSupplier = description;
        return this;
    }

    public void labelValueIsEqualTo(String labelKey) {
        if (this.descriptionSupplier == null) {
            this.descriptionSupplier = () -> "Label value is not correct for label key: %s".formatted(labelKey);
        }
        Assertions.assertThat(actual)
                .as(this.descriptionSupplier)
                .isEqualTo(Translator.getLabelValue(labelKey));
    }

    public void labelValueIsEqualToIgnoringCase(String labelKey) {
        if (this.descriptionSupplier == null) {
            this.descriptionSupplier = () -> "Label value is not correct";
        }
        Assertions.assertThat(actual)
                .as(this.descriptionSupplier)
                .isEqualToIgnoringCase(Translator.getLabelValue(labelKey));
    }
}
