package in.credable.automation.service.module;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.vo.module.ModuleInstanceVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class ModuleService {
    private ModuleService() {
    }

    public static Response getAllModules() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_ALL_MODULES_PATH);
    }

    public static Response createModuleInstance(ModuleInstanceVO moduleInstanceVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(moduleInstanceVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.CREATE_MODULE_INSTANCE_PATH);
    }

    public static Response updateModuleInstance(ModuleInstanceVO moduleInstanceVO) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(moduleInstanceVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.UPDATE_MODULE_INSTANCE_PATH);
    }

    public static Response associateModuleInstanceWithProgram(Long programId, Long moduleInstanceId) {
        return associateModuleInstanceWithProgram(programId, moduleInstanceId, StatusCode.OK);
    }

    public static Response associateModuleInstanceWithProgram(Long programId,
                                                              Long moduleInstanceId,
                                                              StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.ASSOCIATE_MODULE_INSTANCE_WITH_PROGRAM_PATH, programId, moduleInstanceId);
    }

    public static Response getProgramModuleInstancesByProgramId(Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .request(Method.GET, EndPoint.GET_ASSOCIATED_MODULE_INSTANCE_WITH_PROGRAM_PATH, programId);
    }
    /**
     * Get module list for country
     *
     * @param countryId
     * @return {@link Response}
     */
    public static Response getModulesForCountry(Long countryId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_ASSOCIATED_MODULE_FOR_COUNTRY_PATH, countryId);
    }
}
