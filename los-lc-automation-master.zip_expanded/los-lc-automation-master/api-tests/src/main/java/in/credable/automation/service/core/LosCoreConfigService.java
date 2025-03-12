package in.credable.automation.service.core;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

public final class LosCoreConfigService {
    private LosCoreConfigService() {
    }

    /**
     * Fetches the program level validator field mappings
     *
     * @param programId The program id
     * @return {@link Response} object containing response body as {@code List<CustomPropertyMappingVO>}
     */
    public static Response getProgramLevelValidatorFieldMappings(Long programId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getLosCoreHeaders())
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.FETCH_PROGRAM_VALIDATOR_FIELD_MAPPINGS_PATH, programId);
    }
}
