package in.credable.automation.ui.pages.adminapp.loanapplication;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.config.ConfigFactory;
import in.credable.automation.ui.pages.adminapp.module.ModuleConfigurationPage;
import in.credable.automation.ui.pages.modals.loanapplication.CreateCustomSectionModal;
import in.credable.automation.ui.utils.SelenideUtils;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Random;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Log4j2
public class LoanApplicationConfigurationPage {
    private static final SelenideElement PAGE_CONTAINER = $(byCssSelector(".program_configurator_container"));
    private static final SelenideElement BACK_BUTTON = PAGE_CONTAINER.$(byCssSelector(".goto_previous_page > .back_text"));
    private static final SelenideElement PAGE_TITLE = PAGE_CONTAINER.$(byCssSelector(".journey_modules_title"));
    private static final SelenideElement PAGE_INSTRUCTIONS = PAGE_CONTAINER.$(byCssSelector(".journey_creation_instructions"));
    private static final SelenideElement AVAILABLE_SECTIONS_CONTAINER = PAGE_CONTAINER.$(byCssSelector(".available_modules"));
    private static final SelenideElement AVAILABLE_SECTIONS_HEADING = AVAILABLE_SECTIONS_CONTAINER.$(byCssSelector(".modules_heading > div"));
    private static final SelenideElement CREATE_SECTION_BUTTON = AVAILABLE_SECTIONS_CONTAINER.$(byCssSelector(".modules_heading > span"));
    private static final SelenideElement AVAILABLE_SECTIONS_LIST = AVAILABLE_SECTIONS_CONTAINER.$(byCssSelector(".journey_modules_list"));
    private static final ElementsCollection AVAILABLE_SECTIONS_LIST_ITEMS = AVAILABLE_SECTIONS_LIST.$$(byCssSelector(".journey_module_info"));
    private static final String AVAILABLE_SECTIONS_LIST_ITEMS_CSS_SELECTOR = ".journey_modules_list > .journey_module_info";
    private static final Function<String, SelenideElement> AVAILABLE_SECTION_BY_NAME = sectionName ->
            AVAILABLE_SECTIONS_CONTAINER.$(byXpath(".//div[@class='journey_module_name' and text()='%s']".formatted(sectionName)));
    private static final UnaryOperator<SelenideElement> AVAILABLE_SECTION_NAME = sectionListItem -> sectionListItem.$(byCssSelector(".journey_module_name"));
    private static final UnaryOperator<SelenideElement> ADD_SECTION_BUTTON = sectionListItem -> sectionListItem.$(byCssSelector(".journey_module_add"));
    private static final SelenideElement SELECTED_SECTIONS_CONTAINER = PAGE_CONTAINER.$(byCssSelector(".slected_modules"));
    private static final SelenideElement SELECTED_SECTIONS_HEADING = SELECTED_SECTIONS_CONTAINER.$(byCssSelector(".modules_heading > div"));
    private static final SelenideElement RESET_BUTTON = SELECTED_SECTIONS_CONTAINER.$(byCssSelector(".modules_heading > .reset_journey"));
    private static final SelenideElement SELECTED_SECTIONS_LIST = SELECTED_SECTIONS_CONTAINER.$(byCssSelector(".selected_module_list"));
    public static final By SELECTED_SECTIONS_CSS_SELECTOR = byCssSelector(".selected_module_info");
    private static final ElementsCollection SELECTED_SECTIONS_LIST_ITEMS = SELECTED_SECTIONS_LIST.$$(SELECTED_SECTIONS_CSS_SELECTOR);
    private static final Function<String, SelenideElement> SELECTED_SECTION_NAME = sectionName ->
            SELECTED_SECTIONS_CONTAINER.$(byXpath(".//div[@class='selected_module_name' and text()='%s']".formatted(sectionName)));
    private static final Function<String, SelenideElement> SELECTED_SECTION_BY_NAME = sectionName ->
            SELECTED_SECTION_NAME.apply(sectionName).parent();
    private static final Function<String, SelenideElement> SELECTED_SECTION_SEQUENCE_NUMBER_BY_NAME = sectionName ->
            SELECTED_SECTION_BY_NAME.apply(sectionName).parent().$(byCssSelector(".selected_module_count"));
    private static final Function<String, SelenideElement> REMOVE_SECTION_BUTTON = sectionName ->
            SELECTED_SECTION_BY_NAME.apply(sectionName).$(byCssSelector(".remove_selected_module_icon"));
    private static final Function<String, SelenideElement> EDIT_SECTION_BUTTON = sectionName ->
            SELECTED_SECTION_BY_NAME.apply(sectionName).$(byCssSelector(".edit_selected_module_icon"));
    private static final SelenideElement SAVE_BUTTON = PAGE_CONTAINER.$(byCssSelector("div.save_button"));

    public void open() {
        log.info("Opening Loan Application View page");
        String loanApplicationConfigPageUrl = ConfigFactory.getEnvironmentConfig().getLoanApplicationConfigPageUrl();
        SelenideUtils.openUrl(loanApplicationConfigPageUrl);
    }

    public boolean isBackButtonDisplayed() {
        return SelenideUtils.isElementVisible(BACK_BUTTON);
    }

    public ModuleConfigurationPage clickBackButton() {
        log.info("Clicking Back Button");
        BACK_BUTTON.click();
        return new ModuleConfigurationPage();
    }

    public String getPageTitle() {
        return PAGE_TITLE.getText();
    }

    public String getPageInstructions() {
        return PAGE_INSTRUCTIONS.getText();
    }

    public String getAvailableSectionsHeading() {
        return AVAILABLE_SECTIONS_HEADING.getText();
    }

    public String getCreateSectionButtonText() {
        return CREATE_SECTION_BUTTON.getText();
    }

    public String getSelectedSectionsHeading() {
        return SELECTED_SECTIONS_HEADING.getText();
    }

    public String getResetButtonText() {
        if (!RESET_BUTTON.isDisplayed()) {
            selectAnySection();
        }
        return RESET_BUTTON.shouldBe(visible).getText();
    }

    public boolean isSaveButtonDisplayed() {
        return SelenideUtils.isElementVisible(SAVE_BUTTON);
    }

    public String selectAnySection() {
        int availableSectionsSize = AVAILABLE_SECTIONS_LIST_ITEMS.shouldBe(CollectionCondition.sizeGreaterThan(0)).size();
        SelenideElement listItem = AVAILABLE_SECTIONS_LIST_ITEMS.get(new Random().nextInt(availableSectionsSize));
        String sectionName = AVAILABLE_SECTION_NAME.apply(listItem).getText();
        ADD_SECTION_BUTTON.apply(listItem).click();
        return sectionName;
    }

    public boolean isSectionSelected(String sectionName) {
        return SelenideUtils.isElementVisible(SELECTED_SECTION_NAME.apply(sectionName));
    }

    public void removeSelectedSection(String sectionName) {
        REMOVE_SECTION_BUTTON.apply(sectionName).click();
    }

    public CreateCustomSectionModal openCreateCustomSectionModal() {
        CREATE_SECTION_BUTTON.shouldBe(visible).click();
        return new CreateCustomSectionModal();
    }

    public CreateCustomSectionModal openEditCustomSectionModal(String sectionName) {
        EDIT_SECTION_BUTTON.apply(sectionName).click();
        return new CreateCustomSectionModal();
    }

    public boolean isSectionPresentInAvailableSections(String sectionName) {
        return SelenideUtils.isElementVisible(AVAILABLE_SECTION_BY_NAME.apply(sectionName));
    }

    public void resetSelectedSections() {
        RESET_BUTTON.click();
    }

    public void verifyAllSelectedSectionsAreRemoved() {
        SELECTED_SECTIONS_LIST_ITEMS.shouldBe(CollectionCondition.empty);
    }

    public boolean isSaveButtonDisabled() {
        return SelenideUtils.isElementDisabled(SAVE_BUTTON);
    }

    public int getAvailableSectionsCount() {
        return $$(byCssSelector(AVAILABLE_SECTIONS_LIST_ITEMS_CSS_SELECTOR)).size();
    }

    public int getSelectedSectionsCount() {
        Selenide.Wait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(SELECTED_SECTIONS_CSS_SELECTOR));
        return SELECTED_SECTIONS_LIST_ITEMS.shouldBe(CollectionCondition.sizeGreaterThanOrEqual(0)).size();
    }

    public int getSequenceNumberForSelectedSection(String sectionName) {
        return Integer.parseInt(SELECTED_SECTION_SEQUENCE_NUMBER_BY_NAME.apply(sectionName).getText());
    }
}
