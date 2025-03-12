package in.credable.automation.testcases.core;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.config.EnvironmentConfig;
import in.credable.automation.service.core.BorrowerService;
import in.credable.automation.service.vo.Page;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.core.*;
import in.credable.automation.testcases.BaseTest;
import io.restassured.common.mapper.TypeRef;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class BorrowerTest extends BaseTest {

    private static final List<Map<String, Object>> BORROWERS = new ArrayList<>();

    @Test(description = "Test #175 - Verify the functionality of list borrowers API with positive input"
            , dataProvider = "borrowerRequestDataProvider")
    public void verifyFetchingAllBorrowersForProgram(long programId, BorrowerTypeEnum borrowerTypeEnum) {
        ListBorrowersRequestVO listBorrowersRequestVO = ListBorrowersRequestVO.builder()
                .programId(programId)
                .borrowerType(borrowerTypeEnum)
                .approvalStatus(BorrowerStatusEnum.ACTIVE)
                .build();

        ResponseWO<Page<Map<String, Object>>> pageResponseWO = BorrowerService.listBorrowers(listBorrowersRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(pageResponseWO)
                .hasSameStatus(200)
                .hasSameCode("borrower.list.fetch.success")
                .hasSameMessage("Fetch borrowers list successful")
                .timestampIsNotNull()
                .dataIsNotNull();

        FrameworkAssertions.assertThat(pageResponseWO.getData())
                .contentIsNotNull()
                .pageableIsNotNull()
                .totalPagesAreGreaterThanZero()
                .totalElementsAreGreaterThanZero()
                .numberOfElementsAreGreaterThanZero()
                .contentSizeIsGreaterThanZero()
                .hasAdditionalProperty("fields");

        BORROWERS.addAll(pageResponseWO.getData().getContent());
    }

    @Test(description = "Test #240 - Verify the functionality of fetch borrower detail API"
            , dependsOnMethods = "verifyFetchingAllBorrowersForProgram"
            , priority = 1)
    public void verifyFetchingBorrowerDetail() {
        Map<String, Object> borrower = pickRandomBorrower();

        FetchBorrowerRequestVO fetchBorrowerRequestVO = new FetchBorrowerRequestVO();
        fetchBorrowerRequestVO.setProgramId(Long.parseLong(borrower.get("programId").toString()));

        String borrowerId = borrower.get("id").toString();
        ResponseWO<BorrowerResponseVO> responseWO = BorrowerService.fetchBorrower(borrowerId, fetchBorrowerRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("borrower.fetch.success")
                .hasSameMessage("Borrower fetched successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        BorrowerResponseVO borrowerResponseVO = responseWO.getData();
        FrameworkAssertions.assertThat(borrowerResponseVO)
                .borrowerDataIsNotEmpty()
                .fieldsAreNotEmpty()
                .hasSameBorrowerId(borrowerId)
                .hasSameBorrowerData(borrower);
    }

    private Map<String, Object> pickRandomBorrower() {
        Predicate<Map<String, Object>> containsValidId = borrowerData -> borrowerData.containsKey("id")
                && borrowerData.get("id") instanceof String
                && StringUtils.isNotBlank(borrowerData.get("id").toString());

        Predicate<Map<String, Object>> containsValidProgramId = borrowerData -> borrowerData.containsKey("programId")
                && borrowerData.get("programId") instanceof Number
                && borrowerData.get("programId") != null;

        return BORROWERS.stream()
                .filter(containsValidId.and(containsValidProgramId))
                .findAny()
                .orElseThrow();
    }

    @DataProvider(name = "borrowerRequestDataProvider")
    private Object[][] getBorrowerRequestData() {
        EnvironmentConfig environmentConfig = ConfigFactory.getEnvironmentConfig();
        Long dealerProgramId = environmentConfig.getDealerProgramId();
        Long vendorProgramId = environmentConfig.getVendorProgramId();

        return new Object[][]{
                {dealerProgramId, BorrowerTypeEnum.DEALER},
                {vendorProgramId, BorrowerTypeEnum.VENDOR},
        };
    }
}
