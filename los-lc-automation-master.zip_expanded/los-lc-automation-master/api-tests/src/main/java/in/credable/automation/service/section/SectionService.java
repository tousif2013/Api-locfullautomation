package in.credable.automation.service.section;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.vo.section.CreateSectionRequestVO;
import in.credable.automation.service.vo.section.SectionOrderUpdateRequestVO;
import in.credable.automation.service.vo.section.SectionProgramMappingRequestVO;
import in.credable.automation.service.vo.section.UpdateSectionInstanceRequestVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.List;

/**
 * APIs for loan application view page
 *
 * @author Prashant Rana
 */
public final class SectionService {
    private SectionService() {
    }

    /**
     * Fetches all the available sections for the specified program
     *
     * @param programId The program id
     * @return {@link Response} object containing response body as {@code ResponseWO<List<SectionVO>>}
     */
    public static Response listAllAvailableSections(Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.LIST_ALL_AVAILABLE_SECTIONS_PATH, programId);
    }

    /**
     * Creates custom section
     *
     * @param createSectionRequestVO {@link CreateSectionRequestVO} object containing the section details
     * @return {@link Response} object containing response body as {@code ResponseWO<SectionVO>}
     */
    public static Response createCustomSection(CreateSectionRequestVO createSectionRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(createSectionRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.CREATE_CUSTOM_SECTION_PATH);
    }

    /**
     * Updates custom section
     *
     * @param sectionId              Section id for the section to be updated
     * @param createSectionRequestVO Request body
     * @return {@link Response} object containing response body as {@code ResponseWO<SectionVO>}
     */
    public static Response updateCustomSection(Long sectionId, CreateSectionRequestVO createSectionRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(createSectionRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.PUT, EndPoint.UPDATE_CUSTOM_SECTION_PATH, sectionId);
    }

    /**
     * Creates section program mapping for the specified program
     *
     * @param sectionProgramMappingRequestVO {@link SectionProgramMappingRequestVO} object
     * @return {@link Response} object containing response body as {@code ResponseWO<SectionProgramMappingVO>}
     */
    public static Response createSectionProgramMapping(SectionProgramMappingRequestVO sectionProgramMappingRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(sectionProgramMappingRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.CREATE_SECTION_PROGRAM_MAPPING_PATH);
    }

    /**
     * Removes section from the specified program
     *
     * @param sectionProgramMappingId The section program mapping id
     * @return {@link Response} object containing response body as {@code ResponseWO<SectionProgramMappingVO>}
     */
    public static Response removeSectionProgramMapping(Long sectionProgramMappingId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.DELETE, EndPoint.REMOVE_SECTION_PROGRAM_MAPPING_PATH, sectionProgramMappingId);
    }

    /**
     * Fetches all the selected sections for the program
     *
     * @param programId The program id
     * @return {@link Response} object containing response body as {@code ResponseWO<List<SectionProgramMappingVO>>}
     */
    public static Response listAllSelectedSections(Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.LIST_ALL_SELECTED_SECTIONS_FOR_PROGRAM_PATH, programId);
    }

    /**
     * Removes all the sections from the program
     *
     * @param programId The program id to remove all the sections from the program
     * @return {@link Response} object containing response body as {@code List<SectionProgramMappingVO>}
     */
    public static Response removeAllSectionsFromProgram(Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.DELETE, EndPoint.REMOVE_ALL_SECTIONS_FROM_PROGRAM_PATH, programId);
    }

    /**
     * Updates section program mapping
     *
     * @param updateSectionInstanceRequestVO {@link UpdateSectionInstanceRequestVO} object containing the section instance details to be updated.
     *                                       The section instance id and tab id are required in the request body.
     * @return {@link Response} object containing response body as {@code ResponseWO<SectionProgramMappingVO>}
     */
    public static Response updateSectionInstance(UpdateSectionInstanceRequestVO updateSectionInstanceRequestVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(updateSectionInstanceRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.PUT, EndPoint.UPDATE_SECTION_INSTANCE_PATH);
    }

    /**
     * Fetches all the tabs for sections
     *
     * @return {@link Response} object containing response body as {@code ResponseWO<List<SectionTabVO>>}
     */
    public static Response listAllSectionTabs() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.LIST_ALL_SECTION_TABS_PATH);
    }

    /**
     * @param sectionOrderUpdateRequestVOS List of {@link SectionOrderUpdateRequestVO} objects containing the section id and the new order.
     *                                     The section id and the new order are required in the request body.
     * @return {@link Response} object containing response body as {@code ResponseWO<List<SectionProgramMappingVO>>}
     */
    public static Response updateSectionOrder(List<SectionOrderUpdateRequestVO> sectionOrderUpdateRequestVOS) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(sectionOrderUpdateRequestVOS)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.UPDATE_SECTION_ORDER_PATH);
    }

}
