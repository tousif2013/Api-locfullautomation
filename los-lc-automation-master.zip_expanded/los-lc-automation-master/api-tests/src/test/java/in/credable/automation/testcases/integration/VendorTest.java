package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.service.integration.VendorService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.ListVendorsRequestVO;
import in.credable.automation.service.vo.integration.VendorStatusEnum;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Prashant Rana
 */
public class VendorTest extends BaseTest {
    private VendorVO vendorVO;

    @Test(description = "Test #312 - Verify the functionality of add Vendor API in integration service")
    public void verifyAddingVendor() {
        VendorVO vendorRequestVO = DataProviderUtil.manufacturePojo(VendorVO.class);
        ResponseWO<VendorVO> responseWO = VendorService.addVendor(vendorRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();

        FrameworkAssertions.assertThat(responseWO.getData())
                .vendorIdIsNotNull()
                .vendorNameIs(vendorRequestVO.getVendorName())
                .vendorTypeIs(vendorRequestVO.getVendorType())
                .descriptionIs(vendorRequestVO.getDescription())
                .countryCodeIs(vendorRequestVO.getCountryCode())
                .statusIs(VendorStatusEnum.ACTIVE.toString())
                .createdByIs("SYSTEM")
                .createdAtIsNotNull();
        vendorVO = responseWO.getData();
    }

    @Test(description = "Test #305 - Verify the functionality of Vendor list API in integration service"
            , dependsOnMethods = "verifyAddingVendor"
            , priority = 1)
    public void verifyFetchingVendors() {
        ListVendorsRequestVO listVendorsRequestVO = new ListVendorsRequestVO();
        listVendorsRequestVO.setType(vendorVO.getVendorType());
        listVendorsRequestVO.setCountryCode(vendorVO.getCountryCode());

        ResponseWO<List<VendorVO>> responseWO = VendorService.listAllVendors(listVendorsRequestVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();

        Assertions.assertThat(responseWO.getData())
                .isNotNull()
                .isNotEmpty()
                .as(() -> "Created vendor not found in list all vendors API")
                .anyMatch(vendor -> vendorVO.getId().equals(vendor.getId()));

        VendorVO vendorVOFromListAllVendorsAPI = responseWO.getData().stream()
                .filter(vendor -> vendor.getId().equals(vendorVO.getId()))
                .findFirst()
                .orElseThrow();

        FrameworkAssertions.assertThat(vendorVOFromListAllVendorsAPI)
                .isEqualTo(vendorVO);
    }

    @Test(description = "Test #308 - Verify the functionality of Update Vendor API in integration service"
            , dependsOnMethods = "verifyAddingVendor"
            , priority = 2)
    public void verifyUpdatingVendor() {
        VendorVO updateVendorRequestVO = DataProviderUtil.manufacturePojo(VendorVO.class);
        updateVendorRequestVO.setCountryCode("US");
        updateVendorRequestVO.setStatus(VendorStatusEnum.INACTIVE);

        ResponseWO<VendorVO> updateVendorResponseWO = VendorService.updateVendor(updateVendorRequestVO, vendorVO.getId())
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(updateVendorResponseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();

        FrameworkAssertions.assertThat(updateVendorResponseWO.getData())
                .vendorIdIsNotNull()
                .vendorIdIs(vendorVO.getId())
                .vendorNameIs(updateVendorRequestVO.getVendorName())
                .vendorTypeIs(updateVendorRequestVO.getVendorType())
                .descriptionIs(updateVendorRequestVO.getDescription())
                .countryCodeIs(updateVendorRequestVO.getCountryCode())
                .statusIs(updateVendorRequestVO.getStatus().toString())
                .createdByIs(vendorVO.getCreatedBy())
                .createdAtIsNotNull()
                .updatedByIs("SYSTEM")
                .updatedAtIsNotNull();

        ListVendorsRequestVO listVendorsRequestVO = new ListVendorsRequestVO();
        listVendorsRequestVO.setType(updateVendorRequestVO.getVendorType());
        listVendorsRequestVO.setCountryCode(updateVendorRequestVO.getCountryCode());
        ResponseWO<List<VendorVO>> listAllVendorsResponseWO = VendorService.listAllVendors(listVendorsRequestVO)
                .as(new TypeRef<>() {
                });

        Assertions.assertThat(listAllVendorsResponseWO.getData())
                .as(() -> "Updated vendor should not be present in list all vendors API as it is inactive")
                .noneMatch(vendor -> vendorVO.getId().equals(vendor.getId()));
    }
}
