package in.credable.automation.assertions;

import in.credable.automation.service.vo.Page;

import java.util.Objects;

public final class PageAssertion<T> extends CustomAssert<PageAssertion<T>, Page<T>> {

    PageAssertion(Page<T> actual) {
        super(actual, PageAssertion.class);
    }

    public PageAssertion<T> contentIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getContent())) {
            failWithMessage("Content should not be null in response body.");
        }
        return this;
    }

    public PageAssertion<T> pageableIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getPageable())) {
            failWithMessage("Pageable should not be null in response body.");
        }
        return this;
    }

    public PageAssertion<T> pageNumberIs(int expectedValue) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getPageable().getPageNumber(), expectedValue
                , "Page Number");
        return this;
    }

    public PageAssertion<T> pageSizeIs(int expectedValue) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getPageable().getPageSize(), expectedValue
                , "Page Size");
        return this;
    }

    public PageAssertion<T> totalPagesAreGreaterThanZero() {
        isNotNull();
        if (actual.getTotalPages() <= 0) {
            failWithMessage("Total pages should be greater than 0 in response body.");
        }
        return this;
    }

    public PageAssertion<T> totalElementsAreGreaterThanZero() {
        isNotNull();
        if (actual.getTotalElements() <= 0) {
            failWithMessage("Total elements should be greater than 0 in response body.");
        }
        return this;
    }

    public PageAssertion<T> numberOfElementsAreGreaterThanZero() {
        isNotNull();
        if (actual.getNumberOfElements() <= 0) {
            failWithMessage("NumberOfElements should be greater than 0 in response body.");
        }
        return this;
    }

    public PageAssertion<T> contentSizeIsGreaterThanZero() {
        isNotNull();
        if (actual.getContent().isEmpty()) {
            failWithMessage("Content size should not be 0 in response body.");
        }
        return this;
    }

    public PageAssertion<T> hasAdditionalProperty(String propertyName) {
        isNotNull();
        if (!actual.getAdditionalProperties().containsKey(propertyName)) {
            failWithMessage("Response body does not have property %s", propertyName);
        }
        return this;
    }
}
