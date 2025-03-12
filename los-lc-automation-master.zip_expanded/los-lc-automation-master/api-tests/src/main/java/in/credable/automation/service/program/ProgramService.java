package in.credable.automation.service.program;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.vo.program.ProgramFieldMappingVO;
import in.credable.automation.service.vo.program.ProgramThemeVO;
import in.credable.automation.service.vo.program.ProgramVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.io.File;
import java.util.List;

public final class ProgramService {
    private ProgramService() {
    }

    public static Response createProgram(ProgramVO programVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(programVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.CREATE_PROGRAM_PATH);
    }

    public static Response updateProgram(ProgramVO updateProgramVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(updateProgramVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.UPDATE_PROGRAM_PATH);
    }

    public static Response getProgramById(Long programId) {
        return getProgramById(programId, StatusCode.OK);
    }

    public static Response getProgramById(Long programId, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.GET, EndPoint.GET_PROGRAM_PATH, programId);
    }

    public static Response addProgramClientFieldMappings(Long programId, List<ProgramFieldMappingVO> programFieldMappings) {
        return addProgramClientFieldMappings(programId, programFieldMappings, StatusCode.OK);
    }

    public static Response addProgramClientFieldMappings(Long programId,
                                                         List<ProgramFieldMappingVO> programFieldMappings,
                                                         StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(programFieldMappings)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.ADD_PROGRAM_CLIENT_FIELD_MAPPING_PATH, programId);
    }

    public static Response getProgramClientFieldMappings(Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_PROGRAM_CLIENT_FIELD_MAPPING_PATH, programId);
    }

    /**
     * Submit a program
     *
     * @param programVO {@link ProgramVO} object
     * @param programId
     * @return {@link Response} object containing response body as {@code ResponseWO<ProgramVO>}
     */
    public static Response submitProgram(ProgramVO programVO, Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .body(programVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.SUBMIT_PROGRAM_PATH, programId);
    }

    /**
     * Approve a program
     *
     * @param programVO {@link ProgramVO} object
     * @param programId
     * @return {@link Response} object containing response body as {@code ResponseWO<ProgramVO>}
     */
    public static Response approveProgram(ProgramVO programVO, Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .body(programVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.APPROVE_PROGRAM_PATH, programId);
    }

    /**
     * Publish a program
     *
     * @param programVO {@link ProgramVO} object
     * @param programId
     * @return {@link Response} object containing response body as {@code ResponseWO<ProgramVO>}
     */
    public static Response publishProgram(ProgramVO programVO, Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .body(programVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.PUBLISH_PROGRAM_PATH, programId);
    }

    /**
     * Inactive a program
     *
     * @param programVO {@link ProgramVO} object
     * @param programId
     * @return {@link Response} object containing response body as {@code ResponseWO<ProgramVO>}
     */
    public static Response inactiveProgram(ProgramVO programVO, Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .body(programVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.INACTIVE_PROGRAM_PATH, programId);
    }

    /**
     * Rejecting a program
     *
     * @param programVO {@link ProgramVO} object
     * @param programId
     * @return {@link Response} object containing response body as {@code ResponseWO<ProgramVO>}
     */
    public static Response rejectProgram(ProgramVO programVO, Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .body(programVO)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.REJECT_PROGRAM_PATH, programId);
    }

    /**
     * Get program theme
     *
     * @param programId
     * @return {@link Response} object containing response body as {@code ResponseWO<ProgramThemeVO>}
     */
    public static Response getProgramTheme(Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_PROGRAM_THEME_PATH, programId);
    }

    /**
     * Save program theme
     *
     * @param programThemeVO {@link ProgramThemeVO} object
     * @param programId
     * @return {@link Response} object containing response body as {@code ResponseWO<ProgramThemeVO>}
     */
    public static Response saveProgramTheme(ProgramThemeVO programThemeVO, Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .multipart("programId", programThemeVO.getProgramId().toString(), "Text")
                .multipart("headerImage", new File(programThemeVO.getHeaderImage()), "image/png")
                .multipart("headerImageUrl", programThemeVO.getFooterImageUrl(), "Text")
                .multipart("headerSkinColor", programThemeVO.getHeaderSkinColor(), "Text")
                .multipart("primaryButtonColor", programThemeVO.getPrimaryButtonColor(), "Text")
                .multipart("secondaryButtonColor", programThemeVO.getSecondaryButtonColor(), "Text")
                .multipart("fontName", programThemeVO.getFontName(), "Text")
                .multipart("headerImage", new File(programThemeVO.getHeaderImage()), "image/png")
                .multipart("footerImageUrl", programThemeVO.getFooterImageUrl(), "Text")
                .multipart("footerSkinColor", programThemeVO.getFooterSkinColor(), "Text")
                .multipart("footerText", programThemeVO.getFooterText(), "Text")
                .contentType(ContentType.MULTIPART)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.SAVE_PROGRAM_THEME_PATH);
    }

}
