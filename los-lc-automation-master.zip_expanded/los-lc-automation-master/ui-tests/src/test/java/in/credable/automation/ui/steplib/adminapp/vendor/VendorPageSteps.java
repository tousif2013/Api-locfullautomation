package in.credable.automation.ui.steplib.adminapp.vendor;

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
import in.credable.automation.ui.pages.adminapp.vendor.VendorPage;
import in.credable.automation.ui.pages.adminapp.vendor.VendorsTableColumn;
import in.credable.automation.ui.pages.modals.common.BorrowerDetailsModal;
import io.restassured.common.mapper.TypeRef;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

@Log4j2
public class VendorPageSteps {
    private static final EnvironmentConfig ENVIRONMENT_CONFIG = ConfigFactory.getEnvironmentConfig();
    private final VendorPage vendorPage;
    private BorrowerDetailsModal vendorDetailsModal;

    public VendorPageSteps(VendorPage vendorPage) {
        this.vendorPage = vendorPage;
    }

    public VendorPageSteps verifyVendorPageIsOpened() {
        Assertions.assertThat(vendorPage.isNavigatedToVendorsPage())
                .withFailMessage("Vendor Page is not opened")
                .isTrue();
        log.info("Navigated to vendors page");
        return this;
    }

    public void verifyLanguageConversionOnVendorPageUnderProcessedTab() {
        FrameworkAssertions.assertThat(vendorPage.getVendorPageHeaderText())
                .as(() -> "Vendor Page Header text label value is incorrect")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_VENDOR);

        FrameworkAssertions.assertThat(vendorPage.getUploadVendorButtonText())
                .as(() -> "Vendor upload button text label value is incorrect")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_UPLOAD_VENDOR);

        FrameworkAssertions.assertThat(vendorPage.getProcessedTabText())
                .as(() -> "Processed Tab text label value is incorrect")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_PROCESSED);

        FrameworkAssertions.assertThat(vendorPage.getSearchCorporateInputPlaceholder())
                .as(() -> "Search Corporate Input Placeholder text label value is incorrect")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_SEARCH);

        FrameworkAssertions.assertThat(vendorPage.getPaginationFirstButtonText())
                .as(() -> "Pagination first button text is not expected")
                .labelValueIsEqualTo(LabelKey.PAGINATION_FIRST);

        FrameworkAssertions.assertThat(vendorPage.getPaginationLastButtonText())
                .as(() -> "Pagination last button text is not expected")
                .labelValueIsEqualTo(LabelKey.PAGINATION_LAST);

        Map<VendorsTableColumn, String> vendorsTableHeaders = vendorPage.getVendorsTableHeaders();
        for (Map.Entry<VendorsTableColumn, String> vendorsTableColumnStringEntry : vendorsTableHeaders.entrySet()) {
            FrameworkAssertions.assertThat(vendorsTableColumnStringEntry.getValue())
                    .labelValueIsEqualTo(vendorsTableColumnStringEntry.getKey().getLabelKey());
        }
    }

    public void verifyLanguageConversionOnVendorPageUnderApprovalPendingTab() {
        vendorPage.openApprovalPendingTab();
        FrameworkAssertions.assertThat(vendorPage.getApprovalPendingTabText())
                .as(() -> "Approval Pending Tab text label value is incorrect")
                .labelValueIsEqualTo(LabelKey.PAGE_HEADER_APPROVAL_PENDING);

        Map<VendorsTableColumn, String> vendorsTableHeaders = vendorPage.getVendorsTableHeaders();
        for (Map.Entry<VendorsTableColumn, String> vendorsTableColumnStringEntry : vendorsTableHeaders.entrySet()) {
            FrameworkAssertions.assertThat(vendorsTableColumnStringEntry.getValue())
                    .labelValueIsEqualTo(vendorsTableColumnStringEntry.getKey().getLabelKey());
        }
    }

    public VendorPageSteps openVendorDetails() {
        vendorPage.goToPage(ENVIRONMENT_CONFIG.getVendorPageNo());
        String vendorName = ENVIRONMENT_CONFIG.getVendorName();
        vendorDetailsModal = vendorPage.openVendorDetailsModalFor(vendorName);
        Assertions.assertThat(vendorDetailsModal.isModalOpened())
                .as("Vendor details modal is not opened")
                .isTrue();

        Assertions.assertThat(vendorDetailsModal.getBorrowerName())
                .as("Vendor name is not expected")
                .isEqualTo(vendorName);
        return this;
    }

    public void verifyCurrencyShowingOnVendorDetailsModal() {
        Long programId = ENVIRONMENT_CONFIG.getVendorAutoApprovedProgramId();
        // Fetch country details for the program
        Long countryId = ProgramService.getProgramById(programId).as(ProgramVO.class).getCountryId();
        CountryVO countryVO = PlatformService.getCountryDetails(countryId)
                .as(new TypeRef<ResponseWO<CountryVO>>() {
                }).getData();

        // Fetch currency code for the country
        String expectedCurrencySymbol = CurrencyCode.decode(countryVO.getCurrencyCodeIso3()).getCurrencySymbol();

        // Fetch vendor details
        Map<String, String> borrowerDetails = vendorDetailsModal.getBorrowerDetails();

        List<ProgramClientFieldMappingVO> programClientFieldMappings = ProgramService.getProgramClientFieldMappings(programId)
                .as(new TypeRef<>() {
                });

        // Extract recommended limit value from the vendor details
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

        vendorDetailsModal.closeModal();
    }
}
