package in.credable.automation.ui.pages.adminapp.corporate;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import in.credable.automation.service.vo.client.AnchorSubSegmentVO;
import in.credable.automation.service.vo.client.AnchorVO;
import in.credable.automation.service.vo.client.ApprovalStatusEnum;
import in.credable.automation.service.vo.client.MobileNumberVO;
import in.credable.automation.ui.pages.modals.corporate.CorporateDetailsModal;
import in.credable.automation.ui.pages.modals.corporate.CreateCorporateModal;
import in.credable.automation.ui.utils.SelenideUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class CorporatePage {
    private static final SelenideElement PAGE_CONTAINER = $(byCssSelector(".page__container"));
    private static final SelenideElement PAGE_TITLE = PAGE_CONTAINER.$(byXpath(".//div[contains(@class, '__title')]"));
    private static final SelenideElement CREATE_CORPORATE_BUTTON = PAGE_CONTAINER.$(byCssSelector("div.dealer__upload__button"));
    private static final SelenideElement SEARCH_INPUT = PAGE_CONTAINER.$(byCssSelector("input.search_text_field[name='search']"));
    private static final SelenideElement PAGINATION_BOX = PAGE_CONTAINER.$(byCssSelector("div.pagincation-box"));
    private static final SelenideElement PAGINATION_FIRST_BUTTON = PAGINATION_BOX.$(byCssSelector("div.first-c"));
    private static final SelenideElement PAGINATION_LAST_BUTTON = PAGINATION_BOX.$(byCssSelector("div.last-c"));
    private static final SelenideElement CORPORATE_TABLE = PAGE_CONTAINER.$(byCssSelector(".records_table"));
    private static final Function<Integer, SelenideElement> CORPORATE_TABLE_ROW_BY_INDEX = rowIndex ->
            CORPORATE_TABLE.$(byXpath(".//tr[%d]".formatted(rowIndex)));
    private static final BiFunction<Integer, Integer, SelenideElement> CORPORATE_TABLE_CELL_BY_INDEX_AND_COLUMN = (rowIndex, columnIndex) ->
            CORPORATE_TABLE_ROW_BY_INDEX.apply(rowIndex).$(byXpath("./td[%d]".formatted(columnIndex)));
    private static final Function<Integer, SelenideElement> CORPORATE_TABLE_ACTION_CELL_LINK_BY_ROW_INDEX = rowIndex ->
            CORPORATE_TABLE_CELL_BY_INDEX_AND_COLUMN.apply(rowIndex, CorporateTableColumn.ACTION.getColumnIndex());
    private static final Function<Integer, SelenideElement> VIEW_LINK_BY_ROW_INDEX = rowIndex ->
            CORPORATE_TABLE_ACTION_CELL_LINK_BY_ROW_INDEX.apply(rowIndex).$(byCssSelector("div.record_view"));
    private static final Function<Integer, SelenideElement> EDIT_LINK_BY_ROW_INDEX = rowIndex ->
            CORPORATE_TABLE_ACTION_CELL_LINK_BY_ROW_INDEX.apply(rowIndex).$(byCssSelector("div.record_edit"));


    public boolean isNavigatedToCorporatePage() {
        return SelenideUtils.isUrlEndsWith("/corporates");
    }

    public CreateCorporateModal openAddNewCorporateModal() {
        CREATE_CORPORATE_BUTTON.click();
        return new CreateCorporateModal();
    }

    public String getPageTitleText() {
        return PAGE_TITLE.shouldNotBe(empty).getText();
    }

    public String getCreateCorporateButtonText() {
        return CREATE_CORPORATE_BUTTON.shouldNotBe(empty).getText();
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

    public Map<CorporateTableColumn, String> getCorporateTableHeader() {
        return Arrays.stream(CorporateTableColumn.values())
                .collect(Collectors.toMap(Function.identity(),
                        corporateTableColumn -> getCellData(1, corporateTableColumn.getColumnIndex())));
    }

    public AnchorVO getCorporateDetailsOfFirstRow() {
        return getCorporateDetailsOfRow(2);
    }

    public CorporateDetailsModal openCorporateDetailsModalOfFirstRow() {
        VIEW_LINK_BY_ROW_INDEX.apply(2).click();
        return new CorporateDetailsModal();
    }

    public CreateCorporateModal openEditCorporateDetailsModalOfFirstRow() {
        EDIT_LINK_BY_ROW_INDEX.apply(2).click();
        return new CreateCorporateModal();
    }

    public boolean isEditLinkDisabledOfFirstRow() {
        return SelenideUtils.isElementDisabled(EDIT_LINK_BY_ROW_INDEX.apply(2));
    }

    private AnchorVO getCorporateDetailsOfRow(int rowIndex) {
        AnchorVO anchorVO = new AnchorVO();
        anchorVO.setId(getCorporateIdOfRow(rowIndex));
        anchorVO.setAnchorName(getCorporateNameOfRow(rowIndex));
        populateSubSegments(rowIndex, anchorVO);
        anchorVO.setAuthorizedPersonName(getAuthorizedPersonNameOfRow(rowIndex));
        String authorizedPersonMobileNumberWithDialingCode = getAuthorizedPersonMobileNumberOfRow(rowIndex);
        MobileNumberVO mobileNumberVO = new MobileNumberVO(authorizedPersonMobileNumberWithDialingCode, ' ');
        anchorVO.setAuthorizedPersonMobileNumber(mobileNumberVO);
        anchorVO.setApprovalStatus(getApprovalStatusOfRow(rowIndex));
        return anchorVO;
    }

    private Long getCorporateIdOfRow(int rowIndex) {
        return Long.valueOf(getCellData(rowIndex, CorporateTableColumn.CORPORATE_ID.getColumnIndex()));
    }

    private String getCorporateNameOfRow(int rowIndex) {
        return getCellData(rowIndex, CorporateTableColumn.CORPORATE_NAME.getColumnIndex());
    }

    private void populateSubSegments(int rowIndex, AnchorVO anchorVO) {
        String subSegmentNames = getCellData(rowIndex, CorporateTableColumn.CORPORATE_SUBSEGMENT.getColumnIndex());
        if (StringUtils.isNotBlank(subSegmentNames)) {
            ArrayList<AnchorSubSegmentVO> subSegments = new ArrayList<>();
            String[] subsegmentNameList = StringUtils.split(subSegmentNames, ", ");
            for (String subsegmentName : subsegmentNameList) {
                AnchorSubSegmentVO anchorSubSegmentVO = new AnchorSubSegmentVO();
                anchorSubSegmentVO.setSubSegmentName(subsegmentName);
                subSegments.add(anchorSubSegmentVO);
            }
            anchorVO.setSubSegments(subSegments);
        }
    }

    private String getAuthorizedPersonNameOfRow(int rowIndex) {
        return getCellData(rowIndex, CorporateTableColumn.AUTHORISED_PERSON_NAME.getColumnIndex());
    }

    private String getAuthorizedPersonMobileNumberOfRow(int rowIndex) {
        return getCellData(rowIndex, CorporateTableColumn.AUTHORISED_PHONE_NUMBER.getColumnIndex());
    }

    private ApprovalStatusEnum getApprovalStatusOfRow(int rowIndex) {
        return ApprovalStatusEnum.decode(getCellData(rowIndex, CorporateTableColumn.APPROVAL_STATUS.getColumnIndex()));
    }

    private String getCellData(int rowIndex, int columnIndex) {
        return getCell(rowIndex, columnIndex).getText();
    }

    private SelenideElement getCell(int rowIndex, int columnIndex) {
        return CORPORATE_TABLE_CELL_BY_INDEX_AND_COLUMN.apply(rowIndex, columnIndex);
    }
}
