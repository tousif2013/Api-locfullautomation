package in.credable.automation.testcases.anchor;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.service.client.AnchorService;
import in.credable.automation.service.vo.ErrorResponseVO;
import in.credable.automation.service.vo.Page;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.client.*;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorporateTest extends BaseTest {
    private Long anchorId;
    private Long subsegmentId;
    private AnchorVO anchorRequestVO;
    private AnchorSubSegmentVO anchorSubSegmentVO;
    private AnchorVO pendingAnchor;
    private AnchorVO rejectedAnchor;
    private AnchorVO approvedAnchor;

    @Test(description = "Test #269 - Verify the functionality of add anchor API.")
    public void verifyAddingAnchor() {
        anchorRequestVO = DataProviderUtil.manufacturePojo(AnchorVO.class);
        anchorRequestVO.setAnchorCode(null);
        anchorRequestVO.setAnchorType(null);
        anchorSubSegmentVO = DataProviderUtil.manufacturePojo(AnchorSubSegmentVO.class);
        anchorRequestVO.setSubSegments(List.of(anchorSubSegmentVO));
        AnchorVO anchorResponseVO = AnchorService.addAnchor(anchorRequestVO).as(AnchorVO.class);

        FrameworkAssertions.assertThat(anchorResponseVO)
                .anchorIdIsNotNull()
                .hasSameAnchorCode(anchorRequestVO.getAnchorCode())
                .hasSameAnchorName(anchorRequestVO.getAnchorName())
                .hasSameAnchorEmail(anchorRequestVO.getAnchorEmail())
                .hasSameAnchorPAN(anchorRequestVO.getBusinessPAN())
                .hasSameAnchorGSTIN(anchorRequestVO.getGstin())
                .hasSameAnchorAddress(anchorRequestVO.getAnchorAddress())
                .hasSameAnchorCity(anchorRequestVO.getAnchorCity())
                .hasSameAnchorState(anchorRequestVO.getAnchorState())
                .hasSameAnchorCountry(anchorRequestVO.getAnchorCountry())
                .hasSameAnchorPinCode(anchorRequestVO.getAnchorPinCode())
                .hasSameAuthorizedPersonName(anchorRequestVO.getAuthorizedPersonName())
                .hasSameAuthorizedPersonEmail(anchorRequestVO.getAuthorizedPersonEmail())
                .hasSameAuthorizedPersonMobileNo(anchorRequestVO.getAuthorizedPersonMobileNumber())
                .hasSameSubsegmentName(anchorRequestVO.getSubSegments().getFirst().getSubSegmentName())
                .hasSameSubsegmentCode(anchorRequestVO.getSubSegments().getFirst().getSubSegmentCode());
        anchorId = anchorResponseVO.getId();
        pendingAnchor = anchorResponseVO;
        subsegmentId = anchorResponseVO.getSubSegments().getFirst().getId();
    }

    @Test(description = "Test #259 - Verify the functionality of 'get anchor detail' API ."
            , dependsOnMethods = "verifyAddingAnchor"
            , priority = 1)
    public void verifyFetchingAnchorDetail() {
        AnchorVO responseVO = AnchorService.fetchAnchorDetail(anchorId).as(AnchorVO.class);
        FrameworkAssertions.assertThat(responseVO)
                .anchorIdIsNotNull()
                .hasSameAnchorCode(anchorRequestVO.getAnchorCode())
                .hasSameAnchorName(anchorRequestVO.getAnchorName())
                .hasSameAnchorEmail(anchorRequestVO.getAnchorEmail())
                .hasSameAnchorPAN(anchorRequestVO.getBusinessPAN())
                .hasSameAnchorGSTIN(anchorRequestVO.getGstin())
                .hasSameAnchorAddress(anchorRequestVO.getAnchorAddress())
                .hasSameAnchorCity(anchorRequestVO.getAnchorCity())
                .hasSameAnchorState(anchorRequestVO.getAnchorState())
                .hasSameAnchorCountry(anchorRequestVO.getAnchorCountry())
                .hasSameAnchorPinCode(anchorRequestVO.getAnchorPinCode())
                .hasSameAuthorizedPersonName(anchorRequestVO.getAuthorizedPersonName())
                .hasSameAuthorizedPersonEmail(anchorRequestVO.getAuthorizedPersonEmail())
                .hasSameAuthorizedPersonMobileNo(anchorRequestVO.getAuthorizedPersonMobileNumber())
                .hasSameSubsegmentName(anchorRequestVO.getSubSegments().getFirst().getSubSegmentName())
                .hasSameSubsegmentCode(anchorRequestVO.getSubSegments().getFirst().getSubSegmentCode());
    }

    @Test(description = "Test #260 - Verify the functionality of get anchor detail API by invalid input in anchor id.")
    public void verifyFetchingAnchorDetailByInvalidAnchorId() {
        Long invalidAnchorId = -1L;
        ErrorResponseVO errorResponseVO = AnchorService.fetchAnchorDetail(invalidAnchorId, StatusCode.NOT_FOUND)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("CRED101")
                .hasSameErrorMessage("Anchor not found. Anchor Id: %s".formatted(invalidAnchorId))
                .timestampIsNotNull();
    }

    @Test(description = "Test #270 - Verify the functionality of add anchor API by onboarding the anchor multiple time.",
            dependsOnMethods = "verifyAddingAnchor", priority = 1)
    public void verifyAddingAnchorMultipleTime() {

        ErrorResponseVO anchorResponseVO = AnchorService.addAnchor(anchorRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(anchorResponseVO)
                .containsErrorMessage("Duplicate entry");
    }

    @Test(description = "Test #274 - Verify the functionality of update anchor API., " +
            "Test #275 - Verify the functionality of update anchor API for the anchor status as pending.",
            dependsOnMethods = "verifyAddingAnchor", priority = 2)
    public void verifyUpdateAnchor() {
        AnchorVO anchorUpdateVO = DataProviderUtil.manufacturePojo(AnchorVO.class);
        anchorUpdateVO.setId(anchorId);
        anchorUpdateVO.setAnchorCode(null);
        AnchorSubSegmentVO anchorUpdateSubSegmentVO = DataProviderUtil.manufacturePojo(AnchorSubSegmentVO.class);
        anchorUpdateSubSegmentVO.setAnchorId(anchorId);
        anchorUpdateSubSegmentVO.setId(subsegmentId);
        anchorUpdateVO.setSubSegments(List.of(anchorUpdateSubSegmentVO));
        ResponseWO<AnchorVO> anchorResponseVO = AnchorService.updateAnchor(anchorUpdateVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(anchorResponseVO)
                .hasSameStatus(200);
        FrameworkAssertions.assertThat(anchorResponseVO.getData())
                .anchorIdIsNotNull()
                .hasSameAnchorCode(anchorUpdateVO.getAnchorCode())
                .hasSameAnchorName(anchorUpdateVO.getAnchorName())
                .hasSameAnchorEmail(anchorUpdateVO.getAnchorEmail())
                .hasSameAnchorPAN(anchorUpdateVO.getBusinessPAN())
                .hasSameAnchorGSTIN(anchorUpdateVO.getGstin())
                .hasSameAnchorAddress(anchorUpdateVO.getAnchorAddress())
                .hasSameAnchorCity(anchorUpdateVO.getAnchorCity())
                .hasSameAnchorState(anchorUpdateVO.getAnchorState())
                .hasSameAnchorCountry(anchorUpdateVO.getAnchorCountry())
                .hasSameAnchorPinCode(anchorUpdateVO.getAnchorPinCode())
                .hasSameAuthorizedPersonName(anchorUpdateVO.getAuthorizedPersonName())
                .hasSameAuthorizedPersonEmail(anchorUpdateVO.getAuthorizedPersonEmail())
                .hasSameAuthorizedPersonMobileNo(anchorUpdateVO.getAuthorizedPersonMobileNumber())
                .hasSameSubsegmentName(anchorUpdateVO.getSubSegments().getFirst().getSubSegmentName())
                .hasSameSubsegmentCode(anchorUpdateVO.getSubSegments().getFirst().getSubSegmentCode());
    }

    @Test(description = "Test #460-Verify the functionality of 'Approve Anchor' API", priority = 3)
    public void verifyApproveAnchor() {
        AnchorVO createAnchorRequestVO = DataProviderUtil.manufacturePojo(AnchorVO.class);
        AnchorVO anchorResponseVO = AnchorService.addAnchor(createAnchorRequestVO).as(AnchorVO.class);
        FrameworkAssertions.assertThat(anchorResponseVO)
                .anchorIdIsNotNull();

        ResponseWO<String> responseVO = AnchorService.approveAnchor(anchorResponseVO.getId())
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameMessage(ApiMessageConstants.ANCHOR_APPROVE_MESSAGE)
                .hasSameStatus(200);

        approvedAnchor = AnchorService.fetchAnchorDetail(anchorResponseVO.getId()).as(AnchorVO.class);

        FrameworkAssertions.assertThat(approvedAnchor)
                .hasSameApprovalStatus(ApprovalStatusEnum.APPROVED);
    }

    @Test(description = "Test #277 - Verify the functionality of update anchor API for the anchor status as Approved."
            , dependsOnMethods = "verifyApproveAnchor"
            , priority = 3)
    public void verifyUpdatingApprovedAnchor() {
        AnchorVO anchorUpdateVO = DataProviderUtil.manufacturePojo(AnchorVO.class);
        anchorUpdateVO.setId(approvedAnchor.getId());
        anchorUpdateVO.setAnchorCode(null);
        anchorUpdateVO.setApprovalStatus(null);
        AnchorSubSegmentVO anchorUpdateSubSegmentVO = DataProviderUtil.manufacturePojo(AnchorSubSegmentVO.class);
        anchorUpdateVO.setSubSegments(List.of(anchorUpdateSubSegmentVO));
        ResponseWO<AnchorVO> anchorResponseVO = AnchorService.updateAnchor(anchorUpdateVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(anchorResponseVO)
                .hasSameStatus(200);
        FrameworkAssertions.assertThat(anchorResponseVO.getData())
                .anchorIdIsNotNull()
                .hasSameAnchorCode(approvedAnchor.getAnchorCode())
                .hasSameAnchorName(approvedAnchor.getAnchorName())
                .hasSameAnchorEmail(approvedAnchor.getAnchorEmail())
                .hasSameAnchorPAN(approvedAnchor.getBusinessPAN())
                .hasSameAnchorGSTIN(approvedAnchor.getGstin())
                .hasSameAnchorAddress(approvedAnchor.getAnchorAddress())
                .hasSameAnchorCity(approvedAnchor.getAnchorCity())
                .hasSameAnchorState(approvedAnchor.getAnchorState())
                .hasSameAnchorCountry(approvedAnchor.getAnchorCountry())
                .hasSameAnchorPinCode(approvedAnchor.getAnchorPinCode())
                .hasSameAuthorizedPersonName(approvedAnchor.getAuthorizedPersonName())
                .hasSameAuthorizedPersonEmail(approvedAnchor.getAuthorizedPersonEmail())
                .hasSameAuthorizedPersonMobileNo(approvedAnchor.getAuthorizedPersonMobileNumber())
                .hasSameSubsegmentName(anchorUpdateVO.getSubSegments().getFirst().getSubSegmentName())
                .hasSameSubsegmentCode(anchorUpdateVO.getSubSegments().getFirst().getSubSegmentCode())
                .hasSameApprovalStatus(ApprovalStatusEnum.APPROVED);
    }

    @Test(description = "Test #463 -'Verify the functionality of 'Reject Anchor' API .", priority = 4)
    public void verifyRejectAnchor() {
        AnchorVO createAnchorRequestVO = DataProviderUtil.manufacturePojo(AnchorVO.class);
        AnchorVO anchorResponseVO = AnchorService.addAnchor(createAnchorRequestVO).as(AnchorVO.class);
        FrameworkAssertions.assertThat(anchorResponseVO)
                .anchorIdIsNotNull();

        ResponseWO<String> responseVO = AnchorService.rejectAnchor(anchorResponseVO.getId())
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameMessage(ApiMessageConstants.ANCHOR_REJECTED_MESSAGE)
                .hasSameStatus(200);

        rejectedAnchor = AnchorService.fetchAnchorDetail(anchorResponseVO.getId()).as(AnchorVO.class);

        FrameworkAssertions.assertThat(rejectedAnchor)
                .hasSameApprovalStatus(ApprovalStatusEnum.REJECTED);
    }

    @Test(description = "Test #276 - Verify the functionality of update anchor API for the anchor status as Rejected."
            , dependsOnMethods = "verifyRejectAnchor"
            , priority = 4)
    public void verifyUpdatingRejectedAnchor() {
        AnchorVO anchorUpdateVO = DataProviderUtil.manufacturePojo(AnchorVO.class);
        anchorUpdateVO.setId(rejectedAnchor.getId());
        anchorUpdateVO.setAnchorCode(null);
        anchorUpdateVO.setApprovalStatus(null);
        AnchorSubSegmentVO anchorUpdateSubSegmentVO = DataProviderUtil.manufacturePojo(AnchorSubSegmentVO.class);
        anchorUpdateVO.setSubSegments(List.of(anchorUpdateSubSegmentVO));

        ResponseWO<AnchorVO> anchorResponseVO = AnchorService.updateAnchor(anchorUpdateVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(anchorResponseVO)
                .hasSameStatus(200);
        FrameworkAssertions.assertThat(anchorResponseVO.getData())
                .anchorIdIsNotNull()
                .hasSameAnchorCode(rejectedAnchor.getAnchorCode())
                .hasSameAnchorName(anchorUpdateVO.getAnchorName())
                .hasSameAnchorEmail(anchorUpdateVO.getAnchorEmail())
                .hasSameAnchorPAN(anchorUpdateVO.getBusinessPAN())
                .hasSameAnchorGSTIN(anchorUpdateVO.getGstin())
                .hasSameAnchorAddress(anchorUpdateVO.getAnchorAddress())
                .hasSameAnchorCity(anchorUpdateVO.getAnchorCity())
                .hasSameAnchorState(anchorUpdateVO.getAnchorState())
                .hasSameAnchorCountry(anchorUpdateVO.getAnchorCountry())
                .hasSameAnchorPinCode(anchorUpdateVO.getAnchorPinCode())
                .hasSameAuthorizedPersonName(anchorUpdateVO.getAuthorizedPersonName())
                .hasSameAuthorizedPersonEmail(anchorUpdateVO.getAuthorizedPersonEmail())
                .hasSameAuthorizedPersonMobileNo(anchorUpdateVO.getAuthorizedPersonMobileNumber())
                .hasSameSubsegmentName(anchorUpdateVO.getSubSegments().getFirst().getSubSegmentName())
                .hasSameSubsegmentCode(anchorUpdateVO.getSubSegments().getFirst().getSubSegmentCode())
                .hasSameApprovalStatus(ApprovalStatusEnum.PENDING);
    }

    @Test(description = "Test #279 -'Verify the functionality of 'add subsegment' API.", dependsOnMethods = "verifyAddingAnchor")
    public void verifyAddingSubSegmentToAnchor() {
        AnchorSubSegmentVO subSegmentVO = DataProviderUtil.manufacturePojo(AnchorSubSegmentVO.class);
        subSegmentVO.setAnchorId(anchorId);
        String responseBody = AnchorService.addSubsegment(anchorId, subSegmentVO, StatusCode.OK).asString();
        Assertions.assertThat(responseBody)
                .as(() -> "Add subsegment API response is not matching")
                .isEqualTo("SUCCESS");
    }

    @Test(description = "Test #249 -'Verify the functionality of Verify business PAN API.")
    public void verifyBusinessPAN() {
        PANVerificationVO panVerificationVO = DataProviderUtil.manufacturePojo(PANVerificationVO.class);
        PANVerificationVO responseVO = AnchorService.verifyBusinessPAN(panVerificationVO, StatusCode.OK).as(PANVerificationVO.class);
        Assertions.assertThat(responseVO.isValid()).isTrue();
    }

    @Test(description = "Test #252 -'Verify the functionality of fetch gstin list API.")
    public void fetchGSTINList() {
        PANVerificationVO panVerificationVO = DataProviderUtil.manufacturePojo(PANVerificationVO.class);
        ResponseWO<PANVerificationVO> responseVO = AnchorService.fetchGSTINList(panVerificationVO, StatusCode.OK).as(new TypeRef<>() {
        });
    }

    @Test(description = "Test #262 -'Verify the functionality of 'get anchor subsegment' API .", priority = 1, dependsOnMethods = "verifyAddingAnchor")
    public void getAnchorSubsegment() {
        List<AnchorSubSegmentVO> responseVO = Arrays.asList(AnchorService.getAnchorSubsegment(anchorId)
                .as(AnchorSubSegmentVO[].class));
        Assertions.assertThat(responseVO.getFirst().getSubSegmentName()).isEqualTo(anchorSubSegmentVO.getSubSegmentName());
        Assertions.assertThat(responseVO.getFirst().getSubSegmentCode()).isEqualTo(anchorSubSegmentVO.getSubSegmentCode());
    }

    @Test(description = "Test #263 - Verify the functionality of get anchor subsegment API by entering invalid input.")
    public void getAnchorSubsegmentByInvalidInput() {
        Long invalidAnchorId = -1L;
        List<AnchorSubSegmentVO> responseVO = Arrays.asList(AnchorService.getAnchorSubsegment(invalidAnchorId)
                .as(AnchorSubSegmentVO[].class));
        Assertions.assertThat(responseVO)
                .as(() -> "Subsegments should not be fetched for invalid anchor id")
                .isEmpty();
    }

    @Test(description = "Test #265 -'Verify the functionality of 'get anchor list' API ")
    public void getAnchorList() {
        List<AnchorVO> responseVO = Arrays.asList(AnchorService.getAnchorList(StatusCode.OK).as(AnchorVO[].class));
        Assertions.assertThat(responseVO)
                .isNotNull()
                .isNotEmpty();
    }

    @Test(description = "Test #255 -'Verify the functionality of 'filter anchor' API.")
    public void filterAnchor() {
        FilterAnchorVO filterAnchorVO = new FilterAnchorVO();
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", 10);
        map.put("page", 0);
        map.put("sort", List.of("updatedAt,desc"));
        Page<AnchorVO> responseVO = AnchorService.filterAnchor(filterAnchorVO, map).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .pageableIsNotNull()
                .pageNumberIs(0)
                .pageSizeIs(10)
                .contentIsNotNull()
                .contentSizeIsGreaterThanZero()
                .numberOfElementsAreGreaterThanZero()
                .totalPagesAreGreaterThanZero();
    }

    @Test(description = "Test #580 - Verify the functionality of filter anchor API with other valid input."
            , dataProvider = "filterAnchorData"
            , dependsOnMethods = {"verifyAddingAnchor", "verifyApproveAnchor", "verifyRejectAnchor"})
    public void verifyFilterAnchorAPIWithOtherValidInput(Long anchorId) {
        AnchorVO anchor = AnchorService.fetchAnchorDetail(anchorId).as(AnchorVO.class);
        FilterAnchorVO filterAnchorVO = new FilterAnchorVO();
        String searchString = StringUtils.split(anchor.getAnchorName(), '-')[0];
        filterAnchorVO.setSearchString(searchString);
        filterAnchorVO.setApprovalStatus(anchor.getApprovalStatus().getValue());

        Map<String, Object> map = new HashMap<>();
        Page<AnchorVO> pageResponseVO = AnchorService.filterAnchor(filterAnchorVO, map).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(pageResponseVO)
                .contentIsNotNull()
                .contentSizeIsGreaterThanZero()
                .numberOfElementsAreGreaterThanZero()
                .totalPagesAreGreaterThanZero()
                .pageableIsNotNull();

        Assertions.assertThat(pageResponseVO.getContent())
                .as(() -> "Desired anchor is not present in  filter anchor API response")
                .filteredOn(anchorVO -> anchorVO.getId().equals(anchor.getId()))
                .containsOnly(anchor);
    }

    @DataProvider(name = "filterAnchorData")
    private Object[][] filterAnchorData() {
        return new Object[][]{
                {pendingAnchor.getId()},
                {approvedAnchor.getId()},
                {rejectedAnchor.getId()}
        };
    }
}
