package in.credable.automation.assertions.core;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.core.BorrowerResponseVO;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.api.Assertions;

import java.util.Map;

public final class BorrowerResponseAssertion extends CustomAssert<BorrowerResponseAssertion, BorrowerResponseVO> {
    BorrowerResponseAssertion(BorrowerResponseVO actual) {
        super(actual, BorrowerResponseAssertion.class);
    }

    public BorrowerResponseAssertion borrowerDataIsNotEmpty() {
        isNotNull();
        if (actual.getBorrowerData().isEmpty()) {
            failWithMessage("Borrower data is empty");
        }
        return this;
    }

    public BorrowerResponseAssertion fieldsAreNotEmpty() {
        isNotNull();
        if (CollectionUtils.isEmpty(actual.getFields())) {
            failWithMessage("Fields are empty");
        }
        return this;
    }

    public BorrowerResponseAssertion hasSameBorrowerId(String expected) {
        isNotNull();
        if (!actual.getBorrowerData().containsKey("id")) {
            failWithMessage("Borrower id is not present in borrower data");
        } else if (!actual.getBorrowerData().get("id").equals(expected)) {
            failWithMessage("Borrower id is not the same");
        }
        return this;
    }

    public BorrowerResponseAssertion hasSameBorrowerData(Map<String, Object> borrowerData) {
        isNotNull();
        Assertions.assertThat(borrowerData)
                .as("Borrower data is not the same")
                .containsAllEntriesOf(actual.getBorrowerData());
        return this;
    }
}
