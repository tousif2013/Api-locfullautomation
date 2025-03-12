package in.credable.automation.testcases.section;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.service.section.SectionService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.section.*;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.RandomDataGenerator;
import in.credable.automation.utils.TestUtils;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Prashant Rana
 */
public class SectionTest extends BaseTest {
    private static final Long PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();
    private List<SectionVO> availableSections;
    private SectionProgramMappingVO sectionProgramMappingVO;
    private SectionVO customSection;

    @Test(description = "Test #750 - Verify list all available sections API")
    public void verifyFetchingAllAvailableSectionsForProgram() {
        Response response = SectionService.listAllAvailableSections(PROGRAM_ID);
        TestUtils.validateJsonSchemaInClasspath(response, "schema/section-list-json-schema.json");
        ResponseWO<List<SectionVO>> responseWO = response.as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.AVAILABLE_SECTIONS_FETCHED)
                .hasSameCode("sec009")
                .timestampIsNotNull()
                .dataIsNotNull();

        availableSections = responseWO.getData();
    }

    @Test(description = "Test #763 - Verify create section mapping for program API"
            , dependsOnMethods = "verifyFetchingAllAvailableSectionsForProgram"
            , priority = 1)
    public void verifyCreatingSectionMappingForProgram() {
        SectionVO sectionVO = availableSections.getFirst();
        ResponseWO<SectionProgramMappingVO> responseWO = createSectionProgramMapping(sectionVO.getId());

        FrameworkAssertions.assertThat(responseWO.getData())
                .sectionProgramMappingIdIsNotNull()
                .programIdIs(PROGRAM_ID)
                .hasSameSection(sectionVO)
                .sequenceNoIsPositiveValue()
                .isActive();

        sectionProgramMappingVO = responseWO.getData();
    }

    @Test(description = "Test #752 - Verify get section mapping for program API"
            , dependsOnMethods = "verifyCreatingSectionMappingForProgram"
            , priority = 2)
    public void verifyFetchingSectionMappingForProgram() {
        ResponseWO<List<SectionProgramMappingVO>> responseWO = getAllSelectedSections();

        Assertions.assertThat(responseWO.getData())
                .isNotEmpty()
                .contains(sectionProgramMappingVO)
                .allMatch(sectionProgramMappingVO1 -> sectionProgramMappingVO.getIsActive())
                .extracting(SectionProgramMappingVO::getSequenceNo)
                .isSorted();
    }

    @Test(description = "Test #760 - Verify update section instance API"
            , dependsOnMethods = "verifyFetchingSectionMappingForProgram"
            , priority = 3)
    public void verifyUpdatingSectionInstance() {
        UpdateSectionInstanceRequestVO updateSectionInstanceRequestVO = new UpdateSectionInstanceRequestVO();
        updateSectionInstanceRequestVO.setId(sectionProgramMappingVO.getId());
        updateSectionInstanceRequestVO.setTabId(sectionProgramMappingVO.getSectionTab().getTabId());
        updateSectionInstanceRequestVO.setSectionJson("{\"title\":\"loan application view\"}");

        ResponseWO<SectionProgramMappingVO> responseWO = SectionService.updateSectionInstance(updateSectionInstanceRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.SECTION_INSTANCE_UPDATED)
                .hasSameCode("sec007")
                .timestampIsNotNull()
                .dataIsNotNull();

        FrameworkAssertions.assertThat(responseWO.getData())
                .sectionProgramMappingIdIs(sectionProgramMappingVO.getId())
                .sectionJsonIs(updateSectionInstanceRequestVO.getSectionJson());
    }

    @Test(description = "Test #758 - Verify update section order API"
            , dependsOnMethods = "verifyUpdatingSectionInstance"
            , priority = 4)
    public void verifyUpdatingSectionOrder() {
        // 1. Create section program mapping
        SectionVO sectionVO = availableSections.get(1);
        createSectionProgramMapping(sectionVO.getId());

        // 2. Fetch all selected sections for program
        ResponseWO<List<SectionProgramMappingVO>> selectedSectionsResponseWO = getAllSelectedSections();

        List<SectionProgramMappingVO> selectedSections = selectedSectionsResponseWO.getData();
        Assertions.assertThat(selectedSections)
                .as(() -> "Selected section list should not be empty")
                .isNotEmpty();

        // 3. Update sections order
        ArrayList<SectionOrderUpdateRequestVO> sectionOrderUpdateRequestVOS = getSectionOrderUpdateRequestVOS(selectedSections);

        ResponseWO<List<SectionProgramMappingVO>> updateSectionOrderResponseWO = SectionService.updateSectionOrder(sectionOrderUpdateRequestVOS)
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(updateSectionOrderResponseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.SECTION_ORDER_UPDATED)
                .hasSameCode("sec010")
                .timestampIsNotNull()
                .dataIsNotNull();

        // 4. Fetch all selected sections for program again and compare response with updateSectionOrder API
        ResponseWO<List<SectionProgramMappingVO>> updatedSelectedSectionsResponseWO = getAllSelectedSections();
        Assertions.assertThat(updateSectionOrderResponseWO.getData())
                .as(() -> "No sections should be present in selected section list")
                .isNotEmpty()
                .as(() -> "Selected section list should be same as updateSectionOrder API response")
                .containsExactlyInAnyOrderElementsOf(updatedSelectedSectionsResponseWO.getData());
    }

    @Test(description = "Test #753 - Verify remove section mapping for program API"
            , dependsOnMethods = "verifyFetchingSectionMappingForProgram"
            , priority = 5)
    public void verifyRemovingSectionMappingForProgram() {
        ResponseWO<SectionProgramMappingVO> responseWO = SectionService.removeSectionProgramMapping(sectionProgramMappingVO.getId())
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.SECTION_PROGRAM_MAPPINGS_REMOVED)
                .hasSameCode("sec005")
                .timestampIsNotNull()
                .dataIsNotNull();

        FrameworkAssertions.assertThat(responseWO.getData())
                .sectionProgramMappingIdIsNotNull()
                .sectionProgramMappingIdIs(sectionProgramMappingVO.getId())
                .programIdIs(PROGRAM_ID)
                .hasSameSection(sectionProgramMappingVO.getSection())
                .sequenceNoIs(0)
                .isNotActive();
    }

    @Test(description = "Test #756 - Verify remove all section mapping API"
            , dependsOnMethods = "verifyFetchingAllAvailableSectionsForProgram"
            , priority = 6)
    public void verifyRemovingAllSectionMapping() {
        // 1. create section program mapping
        SectionVO sectionVO = availableSections.get(2);
        createSectionProgramMapping(sectionVO.getId());

        // 2. Remove all sections from program
        ResponseWO<List<SectionProgramMappingVO>> responseWO = SectionService.removeAllSectionsFromProgram(PROGRAM_ID)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.ALL_SECTIONS_PROGRAM_MAPPINGS_REMOVED)
                .hasSameCode("sec008")
                .timestampIsNotNull()
                .dataIsNotNull();

        Assertions.assertThat(responseWO.getData())
                .isNotEmpty()
                .allSatisfy(sectionProgramMappingVO -> Assertions.assertThat(sectionProgramMappingVO)
                        .isNotNull()
                        .hasNoNullFieldsOrPropertiesExcept("sectionJson"))
                .allSatisfy(sectionProgramMappingVO -> FrameworkAssertions.assertThat(sectionProgramMappingVO)
                        .isNotActive()
                        .sequenceNoIs(0));

        // 3. Fetch all selected sections for program
        ResponseWO<List<SectionProgramMappingVO>> selectedSectionsResponseWO = getAllSelectedSections();

        Assertions.assertThat(selectedSectionsResponseWO.getData())
                .as(() -> "No sections should be present in selected section list")
                .isEmpty();
    }

    @Test(description = "Test #754 - Verify create custom section API"
            , priority = 7)
    public void verifyCreatingCustomSection() {
        CreateSectionRequestVO createSectionRequestVO = new CreateSectionRequestVO();
        createSectionRequestVO.setSectionName(RandomDataGenerator.generateRandomString(10));
        createSectionRequestVO.setProgramId(PROGRAM_ID);
        ResponseWO<SectionVO> responseWO = SectionService.createCustomSection(createSectionRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.CUSTOM_SECTION_CREATED)
                .hasSameCode("sec002")
                .timestampIsNotNull()
                .dataIsNotNull();

        customSection = responseWO.getData();

        FrameworkAssertions.assertThat(customSection)
                .sectionIdIsNotNull()
                .sectionNameIs(createSectionRequestVO.getSectionName())
                .sectionCodeIs(RegExUtils.replaceAll(StringUtils.upperCase(createSectionRequestVO.getSectionName()), " ", "_"))
                .sectionTypeIs("FORM")
                .isCustomSection()
                .isEditableSection();
    }

    @Test(description = "Test #755 - Verify update custom section API"
            , dependsOnMethods = "verifyCreatingCustomSection"
            , priority = 8)
    public void verifyUpdatingCustomSection() {
        CreateSectionRequestVO createSectionRequestVO = new CreateSectionRequestVO();
        createSectionRequestVO.setSectionName(RandomDataGenerator.generateRandomString(10));
        createSectionRequestVO.setProgramId(PROGRAM_ID);
        ResponseWO<SectionVO> responseWO = SectionService.updateCustomSection(customSection.getId(), createSectionRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.CUSTOM_SECTION_UPDATED)
                .hasSameCode("sec003")
                .timestampIsNotNull()
                .dataIsNotNull();

        FrameworkAssertions.assertThat(responseWO.getData())
                .sectionIdIsNotNull()
                .sectionNameIs(createSectionRequestVO.getSectionName())
                .sectionCodeIs(RegExUtils.replaceAll(StringUtils.upperCase(createSectionRequestVO.getSectionName()), " ", "_"))
                .sectionTypeIs("FORM")
                .isCustomSection()
                .isEditableSection();
    }

    @Test(description = "Test #762 - Verify List All Section Tabs API"
            , priority = 9)
    public void verifyFetchingAllSectionTabs() {
        ResponseWO<List<SectionTabVO>> responseWO = SectionService.listAllSectionTabs()
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.SECTION_TABS_FETCHED)
                .hasSameCode("sec011")
                .timestampIsNotNull()
                .dataIsNotNull();

        Assertions.assertThat(responseWO.getData())
                .isNotEmpty()
                .allSatisfy(sectionTabVO -> Assertions.assertThat(sectionTabVO)
                        .isNotNull()
                        .hasNoNullFieldsOrProperties());
    }

    private ResponseWO<SectionProgramMappingVO> createSectionProgramMapping(Long sectionId) {
        SectionProgramMappingRequestVO sectionProgramMappingRequestVO = new SectionProgramMappingRequestVO();
        sectionProgramMappingRequestVO.setSectionId(sectionId);
        sectionProgramMappingRequestVO.setProgramId(PROGRAM_ID);
        ResponseWO<SectionProgramMappingVO> sectionProgramMappingVOResponseWO = SectionService.createSectionProgramMapping(sectionProgramMappingRequestVO)
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(sectionProgramMappingVOResponseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.SECTION_PROGRAM_MAPPINGS_CREATED)
                .hasSameCode("sec004")
                .timestampIsNotNull()
                .dataIsNotNull();
        return sectionProgramMappingVOResponseWO;
    }

    private ResponseWO<List<SectionProgramMappingVO>> getAllSelectedSections() {
        ResponseWO<List<SectionProgramMappingVO>> selectedSectionsResponseWO = SectionService.listAllSelectedSections(PROGRAM_ID)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(selectedSectionsResponseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.SECTION_PROGRAM_MAPPINGS_FETCHED)
                .hasSameCode("sec006")
                .timestampIsNotNull()
                .dataIsNotNull();
        return selectedSectionsResponseWO;
    }

    private ArrayList<SectionOrderUpdateRequestVO> getSectionOrderUpdateRequestVOS(List<SectionProgramMappingVO> selectedSections) {
        ArrayList<SectionOrderUpdateRequestVO> sectionOrderUpdateRequestVOS = new ArrayList<>();
        int selectedSectionsSize = selectedSections.size();
        for (int i = 0; i < selectedSectionsSize; i++) {
            SectionOrderUpdateRequestVO sectionOrderUpdateRequestVO = new SectionOrderUpdateRequestVO();
            sectionOrderUpdateRequestVO.setSectionInstanceId(selectedSections.get(i).getId());
            sectionOrderUpdateRequestVO.setSequenceNo(selectedSectionsSize - i);
            sectionOrderUpdateRequestVOS.add(sectionOrderUpdateRequestVO);
        }
        return sectionOrderUpdateRequestVOS;
    }
}
