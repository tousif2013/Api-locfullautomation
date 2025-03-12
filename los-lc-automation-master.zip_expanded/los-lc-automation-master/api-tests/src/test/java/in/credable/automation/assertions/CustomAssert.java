package in.credable.automation.assertions;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;

import java.util.Objects;

public abstract class CustomAssert<SELF extends AbstractAssert<SELF, ACTUAL>, ACTUAL> extends AbstractAssert<SELF, ACTUAL> {

    protected CustomAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    @Override
    public SELF isEqualTo(Object expected) {
        return super.isEqualTo(expected);
    }

    /**
     * Compares only not null fields from actual with expected object
     *
     * @param expected The expected object
     */
    public void compareAllNotNullFields(Object expected) {
        super.usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoreAllActualNullFields(true)
                        .build())
                .isEqualTo(expected);
    }

    protected void failureWithActualExpectedForStringComparison(String actual, String expected, String propertyName) {
        if (!StringUtils.equals(actual, expected)) {
            throw failureWithActualExpected(actual, expected,
                    "Expecting %s to be <%s> but was <%s>",
                    propertyName,
                    expected,
                    actual);
        }
    }

    protected void failureWithActualExpectedForNumberComparison(Number actual, Number expected, String propertyName) {
        if (!Objects.equals(actual, expected)) {
            throw failureWithActualExpected(actual, expected,
                    "Expecting %s to be <%d> but was <%d>",
                    propertyName,
                    expected,
                    actual);
        }
    }

    protected void failureWithActualExpectedForBooleanComparison(Boolean actual, Boolean expected, String propertyName) {
        if (!Objects.equals(actual, expected)) {
            throw failureWithActualExpected(actual, expected,
                    "Expecting <%s> to be <%b> but was <%b>",
                    propertyName,
                    expected,
                    actual);
        }
    }

    protected void failureWithActualExpectedForEnumComparison(Enum<?> actual, Enum<?> expected, String propertyName) {
        if (!Objects.equals(actual, expected)) {
            throw failureWithActualExpected(actual, expected,
                    "Expecting <%s> to be <%s> but was <%s>",
                    propertyName,
                    expected,
                    actual);
        }
    }

    protected void failureWithActualExpectedForObjectComparison(Object actual, Object expected, String propertyName) {
        Assertions.assertThat(actual)
                .as("<%s> is not matching with expected value", propertyName)
                .isEqualTo(expected);
    }

    protected void propertyIsNotNull(Object property, String propertyName) {
        if (Objects.isNull(property)) {
            failWithMessage("<%s> should not be null", propertyName);
        }
    }

    protected void propertyIsNull(Object propertyValue, String propertyName) {
        if (Objects.nonNull(propertyValue)) {
            failWithMessage("<%s> should be null", propertyName);
        }
    }
}
