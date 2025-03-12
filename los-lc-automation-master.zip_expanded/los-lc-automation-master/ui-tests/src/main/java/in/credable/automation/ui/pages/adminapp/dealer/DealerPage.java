package in.credable.automation.ui.pages.adminapp.dealer;

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
public class DealerPage {
    private static final SelenideElement DEALERS_PAGE_CONTAINER = $(byCssSelector("div.dealers__info"));
    private static final SelenideElement DEALERS_PAGE_HEADER = DEALERS_PAGE_CONTAINER.$(byCssSelector("div.dealer__header"));
    private static final SelenideElement DEALERS_PAGE_TITLE = DEALERS_PAGE_HEADER.$(byCssSelector("div.dealer__title"));
    private static final SelenideElement UPLOAD_DEALER_BUTTON = DEALERS_PAGE_HEADER.$(byCssSelector("div.dealer__upload__button"));
    private static final SelenideElement DEALERS_PAGE_TAB_BAR = DEALERS_PAGE_CONTAINER.$(byCssSelector("div.tab_bar"));
    private static final ElementsCollection DEALERS_PAGE_TAB_OPTIONS = DEALERS_PAGE_TAB_BAR.$$(byCssSelector("div.tab_option"));
    private static final SelenideElement SEARCH_INPUT = DEALERS_PAGE_CONTAINER.$(byCssSelector("input.search_text_field[name='search']"));
    private static final SelenideElement PAGINATION_BOX = DEALERS_PAGE_CONTAINER.$(byCssSelector("div.pagincation-box"));
    private static final SelenideElement CURRENT_PAGE_INPUT = PAGINATION_BOX.$(byName("current__page"));
    private static final SelenideElement PAGINATION_FIRST_BUTTON = PAGINATION_BOX.$(byCssSelector("div.first-c"));
    private static final SelenideElement PAGINATION_LAST_BUTTON = PAGINATION_BOX.$(byCssSelector("div.last-c"));
    private static final SelenideElement DEALERS_TABLE = DEALERS_PAGE_CONTAINER.$(byCssSelector("table.records_table"));
    private static final ElementsCollection TABLE_ROWS = DEALERS_TABLE.$$(byCssSelector("tr"));
    private static final ElementsCollection HEADER_COLUMNS = TABLE_ROWS.first().$$(byCssSelector("td.th_header"));

    public boolean isNavigatedToDealersPage() {
        return SelenideUtils.isUrlEndsWith("/dealer");
    }

    public String getDealerPageHeaderText() {
        return DEALERS_PAGE_TITLE.shouldBe(visible).getText();
    }

    public String getUploadDealerButtonText() {
        return UPLOAD_DEALER_BUTTON.shouldBe(visible).getText();
    }

    public String getProcessedTabText() {
        return DEALERS_PAGE_TAB_OPTIONS.first().shouldBe(visible).getText();
    }

    public String getApprovalPendingTabText() {
        return DEALERS_PAGE_TAB_OPTIONS.last().shouldBe(visible).getText();
    }

    public void openApprovalPendingTab() {
        DEALERS_PAGE_TAB_OPTIONS.last().shouldBe(visible).click();
        Selenide.Wait().until(ExpectedConditions.attributeContains(DEALERS_PAGE_TAB_OPTIONS.last().getWrappedElement()
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

    public Map<DealersTableColumn, String> getDealersTableHeaders() {
        return Arrays.stream(DealersTableColumn.values())
                .collect(Collectors.toMap(Function.identity(),
                        dealersTableColumn -> HEADER_COLUMNS.get(dealersTableColumn.getColumnIndex() - 1).getText()));
    }

    public void goToPage(int pageNumber) {
        CURRENT_PAGE_INPUT.shouldBe(visible).setValue(String.valueOf(pageNumber)).press(Keys.ENTER);
        AppLoader.waitForLoader();
        log.info("Opened page: {} for borrowers", pageNumber);
    }

    public BorrowerDetailsModal openDealerDetailsModalFor(String dealerName) {
        SelenideElement dealerRow = TABLE_ROWS
                .findBy(new WebElementCondition("") {
                    @Nonnull
                    @Override
                    public CheckResult check(Driver driver, WebElement element) {
                        String currentDealerName = element.findElement(By.tagName("td")).getText();
                        return new CheckResult(currentDealerName.equals(dealerName), currentDealerName);
                    }
                });
        dealerRow.$(byCssSelector("div.record_view")).click(ClickOptions.usingJavaScript());
        AppLoader.waitForLoader();
        BorrowerDetailsModal borrowerDetailsModal = new BorrowerDetailsModal();
        borrowerDetailsModal.waitForModal();
        log.info("Opened dealer details modal for: {}", dealerName);
        return borrowerDetailsModal;
    }
}
