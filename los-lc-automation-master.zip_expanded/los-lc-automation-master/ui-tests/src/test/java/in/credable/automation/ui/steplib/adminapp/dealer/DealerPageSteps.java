package in.credable.automation.ui.steplib.adminapp.dealer;

import in.credable.automation.enums.StandardField;
import in.credable.automation.service.platform.PlatformService;
import in.credable.automation.service.program.ProgramService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.client.ClientFieldMappingVO;
import in.credable.automation.service.vo.platform.CountryVO;
import in.credable.automation.service.vo.program.ProgramClientFieldMappingVO;
import in.credable.automation.service.vo.program.ProgramVO;
import in.credable.automation.ui.assertions.FrameworkAssertions;
import in.credable.automation.ui.config.ConfigFactory;
import in.credable.automation.ui.config.EnvironmentConfig;
import in.credable.automation.ui.enums.CurrencyCode;
import in.credable.automation.ui.language.LabelKey;
import in.credable.automation.ui.pages.adminapp.dealer.DealerPage;
import in.credable.automation.ui.pages.adminapp.dealer.DealersTableColumn;
import in.credable.automation.ui.pages.modals.common.BorrowerDetailsModal;
import io.restassured.common.mapper.TypeRef;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

@Log4j2
public class DealerPageSteps {
    private static final EnvironmentConfig ENVIRONMENT_CONFIG = ConfigFactory.getEnvironmentConfig();
    private final DealerPage dealerPage;
    private BorrowerDetailsModal dealerDetailsModal;

    public DealerPageSteps(DealerPage dealerPage) {
        this.dealerPage = dealerPage;
    }

    public DealerPageSteps verifyDealerPageIsOpened() {
        Assertions.assertThat(dealerPage.isNavigatedToDealersPage())
                .as("Dealer Page is not opened")
                .isTrue();
        log.info("Navigated to dealers page");
        return this;
    }

    public void verifyLanguageConversionOnDealersPageUnderProcessedTab() {
        FrameworkAssertions.assertThat(dealerPage.getDealerPageHeaderText())
                .as(() -> "Dealer Page Header text label value is incorrect")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_DEALER);

        FrameworkAssertions.assertThat(dealerPage.getUploadDealerButtonText())
                .as(() -> "Dealer upload button text label value is incorrect")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_UPLOAD_DEALER);

        FrameworkAssertions.assertThat(dealerPage.getProcessedTabText())
                .as(() -> "Processed Tab text label value is incorrect")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_PROCESSED);

        FrameworkAssertions.assertThat(dealerPage.getSearchCorporateInputPlaceholder())
                .as(() -> "Search Corporate Input Placeholder text label value is incorrect")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_SEARCH);

        FrameworkAssertions.assertThat(dealerPage.getPaginationFirstButtonText())
                .as(() -> "Pagination first button text is not expected")
                .labelValueIsEqualTo(LabelKey.PAGINATION_FIRST);

        FrameworkAssertions.assertThat(dealerPage.getPaginationLastButtonText())
                .as(() -> "Pagination last button text is not expected")
                .labelValueIsEqualTo(LabelKey.PAGINATION_LAST);

        Map<DealersTableColumn, String> dealersTableHeaders = dealerPage.getDealersTableHeaders();
        for (Map.Entry<DealersTableColumn, String> dealersTableColumnStringEntry : dealersTableHeaders.entrySet()) {
            FrameworkAssertions.assertThat(dealersTableColumnStringEntry.getValue())
                    .labelValueIsEqualTo(dealersTableColumnStringEntry.getKey().getLabelKey());
        }
    }

    public void verifyLanguageConversionOnDealersPageUnderApprovalPendingTab() {
        dealerPage.openApprovalPendingTab();
        FrameworkAssertions.assertThat(dealerPage.getApprovalPendingTabText())
                .as(() -> "Approval Pending Tab text label value is incorrect")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_APPROVAL_PENDING);

        Map<DealersTableColumn, String> dealersTableHeaders = dealerPage.getDealersTableHeaders();
        for (Map.Entry<DealersTableColumn, String> dealersTableColumnStringEntry : dealersTableHeaders.entrySet()) {
            FrameworkAssertions.assertThat(dealersTableColumnStringEntry.getValue())
                    .labelValueIsEqualTo(dealersTableColumnStringEntry.getKey().getLabelKey());
        }
    }

    public DealerPageSteps openDealerDetails() {
        dealerPage.goToPage(ENVIRONMENT_CONFIG.getDealerPageNo());
        String dealerName = ENVIRONMENT_CONFIG.getDealerName();
        dealerDetailsModal = dealerPage.openDealerDetailsModalFor(dealerName);
        Assertions.assertThat(dealerDetailsModal.isModalOpened())
                .as("Dealer details modal is not opened")
                .isTrue();

        Assertions.assertThat(dealerDetailsModal.getBorrowerName())
                .as("Dealer name is not expected")
                .isEqualTo(dealerName);
        return this;
    }

    public void verifyCurrencyShowingOnDealerDetailsModal() {
        Long programId = ENVIRONMENT_CONFIG.getDealerAutoApprovedProgramId();
        // Fetch country details for the program
        Long countryId = ProgramService.getProgramById(programId).as(ProgramVO.class).getCountryId();
        CountryVO countryVO = PlatformService.getCountryDetails(countryId)
                .as(new TypeRef<ResponseWO<CountryVO>>() {
                }).getData();

        // Fetch currency code for the country
        String expectedCurrencySymbol = CurrencyCode.decode(countryVO.getCurrencyCodeIso3()).getCurrencySymbol();

        // Fetch dealer details
        Map<String, String> borrowerDetails = dealerDetailsModal.getBorrowerDetails();

        List<ProgramClientFieldMappingVO> programClientFieldMappings = ProgramService.getProgramClientFieldMappings(programId)
                .as(new TypeRef<>() {
                });

        // Extract recommended limit value from the dealer details
        String recommendedLimitClientFieldName = programClientFieldMappings.stream()
                .map(ProgramClientFieldMappingVO::getClientFieldMappingVO)
                .filter(clientFieldMappingVO -> StringUtils.equals(clientFieldMappingVO.getStandardFieldName(),
                        StandardField.RECOMMENDED_LIMIT.getStandardFieldName()))
                .map(ClientFieldMappingVO::getClientFieldName)
                .findFirst()
                .orElseThrow();
        String recommendedLimit = borrowerDetails.get(recommendedLimitClientFieldName);

        // Verify currency symbol is displayed in the recommended limit
        Assertions.assertThat(recommendedLimit)
                .as(() -> "Recommended limit is not displayed with the expected currency symbol")
                .startsWith(expectedCurrencySymbol);

        // Extract total sales in last FY value from the dealer details
        String totalSalesLastFYClientFieldName = programClientFieldMappings.stream()
                .map(ProgramClientFieldMappingVO::getClientFieldMappingVO)
                .filter(clientFieldMappingVO -> StringUtils.equals(clientFieldMappingVO.getStandardFieldName(),
                        StandardField.TOTAL_SALES_LAST_FY.getStandardFieldName()))
                .map(ClientFieldMappingVO::getClientFieldName)
                .findFirst()
                .orElseThrow();
        String totalSalesLastFY = borrowerDetails.get(totalSalesLastFYClientFieldName);

        // Verify currency symbol is displayed in the total sales in last FY
        Assertions.assertThat(totalSalesLastFY)
                .as(() -> "Total Sales to Dealer (last FY) (in INR) is not displayed with the expected currency symbol")
                .startsWith(expectedCurrencySymbol);

        dealerDetailsModal.closeModal();
    }
}
