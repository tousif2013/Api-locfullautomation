package in.credable.automation.service.language;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

import java.util.Map;

public final class LanguageService {
    private LanguageService() {
    }

    public static Map<String, String> getLanguageMap(final String languageCodeIso2) {
        return RestAssuredClient
                .withBaseUrl(EndPoint.LOCIZE_API_BASE_URL)
                .enableLoggingFilters(true, true)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.LOCIZE_API_GET_TRANSLATIONS_PATH, languageCodeIso2)
                .as(new TypeRef<>() {
                });
    }

}
