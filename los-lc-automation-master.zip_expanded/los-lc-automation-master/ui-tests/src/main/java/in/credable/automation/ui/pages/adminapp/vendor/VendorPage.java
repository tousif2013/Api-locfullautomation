package in.credable.automation.ui.pages.adminapp.vendor;

import com.codeborne.selenide.*;
import in.credable.automation.ui.pages.components.AppLoader;
import in.credable.automation.ui.pages.modals.common.BorrowerDetailsModal;
import in.credable.automation.ui.utils.SelenideUtils;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class VendorPage {
    private static final SelenideElement VENDORS_PAGE_CONTAINER = $(byCssSelector("div.dealers__info"));
    private static final SelenideElement VENDORS_PAGE_HEADER = VENDORS_PAGE_CONTAINER.$(byCssSelector("div.dealer__header"));
    private static final SelenideElement VENDORS_PAGE_TITLE = VENDORS_PAGE_HEADER.$(byCssSelector("div.dealer__title"));
    private static final SelenideElement UPLOAD_VENDOR_BUTTON = VENDORS_PAGE_HEADER.$(byCssSelector("div.dealer__upload__button"));
    private static final SelenideElement VENDORS_PAGE_TAB_BAR = VENDORS_PAGE_CONTAINER.$(byCssSelector("div.tab_bar"));
    private static final ElementsCollection VENDORS_PAGE_TAB_OPTIONS = VENDORS_PAGE_TAB_BAR.$$(byCssSelector("div.tab_option"));
    private static final SelenideElement SEARCH_INPUT = VENDORS_PAGE_CONTAINER.$(byCssSelector("input.search_text_field[name='search']"));
    private static final SelenideElement PAGINATION_BOX = VENDORS_PAGE_CONTAINER.$(byCssSelector("div.pagincation-box"));
    private static final SelenideElement CURRENT_PAGE_INPUT = PAGINATION_BOX.$(byName("current__page"));
    private static final SelenideElement PAGINATION_FIRST_BUTTON = PAGINATION_BOX.$(byCssSelector("div.first-c"));
    private static final SelenideElement PAGINATION_LAST_BUTTON = PAGINATION_BOX.$(byCssSelector("div.last-c"));
    private static final SelenideElement VENDORS_TABLE = VENDORS_PAGE_CONTAINER.$(byCssSelector("table.records_table"));
    private static final ElementsCollection TABLE_ROWS = VENDORS_TABLE.$$(byCssSelector("tr"));
    private static final ElementsCollection HEADER_COLUMNS = TABLE_ROWS.first().$$(byCssSelector("td.th_header"));

    public boolean isNavigatedToVendorsPage() {
        return SelenideUtils.isUrlEndsWith("/vendor");
    }

    public String getVendorPageHeaderText() {
        return VENDORS_PAGE_TITLE.shouldBe(visible).getText();
    }

    public String getUploadVendorButtonText() {
        return UPLOAD_VENDOR_BUTTON.shouldBe(visible).getText();
    }

    public String getProcessedTabText() {
        return VENDORS_PAGE_TAB_OPTIONS.first().shouldBe(visible).getText();
    }

    public String getApprovalPendingTabText() {
        return VENDORS_PAGE_TAB_OPTIONS.last().shouldBe(visible).getText();
    }

    public void openApprovalPendingTab() {
        VENDORS_PAGE_TAB_OPTIONS.last().shouldBe(visible).click();
        Selenide.Wait().until(ExpectedConditions.attributeContains(VENDORS_PAGE_TAB_OPTIONS.last().getWrappedElement()
                , "ng-reflect-ng-class", "active"));
    }

    public String getSearchCorporateInputPlaceholder() {
        return SEARCH_INPUT.shouldBe(Condition.visible).getAttribute("placeholder");
    }

    public String getPaginationFirstButtonText() {
        return PAGINATION_FIRST_BUTTON.shouldBe(Condition.visible).getText();
    }

    public String getPaginationLastButtonText() {
        return PAGINATION_LAST_BUTTON.shouldBe(Condition.visible).getText();
    }

    public Map<VendorsTableColumn, String> getVendorsTableHeaders() {
        return Arrays.stream(VendorsTableColumn.values())
                .collect(Collectors.toMap(Function.identity(),
                        vendorsTableColumn -> HEADER_COLUMNS.get(vendorsTableColumn.getColumnIndex() - 1).getText()));
    }

    public void goToPage(int pageNumber) {
        CURRENT_PAGE_INPUT.shouldBe(visible).setValue(String.valueOf(pageNumber)).press(Keys.ENTER);
        AppLoader.waitForLoader();
        log.info("Opened page: {} for borrowers", pageNumber);
    }

    public BorrowerDetailsModal openVendorDetailsModalFor(String vendorName) {
        SelenideElement vendorRow = TABLE_ROWS
                .findBy(new WebElementCondition("") {
                    @Nonnull
                    @Override
                    public CheckResult check(Driver driver, WebElement element) {
                        String currentVendorName = element.findElement(By.tagName("td")).getText();
                        return new CheckResult(currentVendorName.equals(vendorName), currentVendorName);
                    }
                });
        vendorRow.$(byCssSelector("div.record_view")).click(ClickOptions.usingJavaScript());
        AppLoader.waitForLoader();
        BorrowerDetailsModal borrowerDetailsModal = new BorrowerDetailsModal();
        borrowerDetailsModal.waitForModal();
        log.info("Opened vendor details modal for: {}", vendorName);
        return borrowerDetailsModal;
    }
}
