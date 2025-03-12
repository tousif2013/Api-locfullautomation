package in.credable.automation.service.platform;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

/**
 * @author Prashant Rana
 */
public final class PlatformService {
    private PlatformService() {
    }

    /**
     * Fetches country details
     *
     * @param countryId The country id
     * @return {@link Response} object containing response body as {@code ResponseWO<CountryVO>}
     */
    public static Response getCountryDetails(Long countryId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_COUNTRY_DETAILS_PATH, countryId);
    }
    /**
     * Fetch all the product Types
     *
     * @return {@link Response} object containing response body as {@code List<ProductTypesVO>}
     */
    public static Response getProductTypes() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_PRODUCT_TYPES_PATH);
    }
    /**
     * Fetch all the product Categories
     *
     * @return {@link Response}
     */
    public static Response getProductCategories() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_PRODUCT_CATEGORIES_PATH);
    }
    /**
     * Fetch all the Country
     *
     * @return {@link Response} object containing response body as {@code List<CountryVO>}
     */
    public static Response getAllCountry() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_ALL_COUNTRY_PATH);
    }
    /**
     * Fetch all the Language
     *
     * @return {@link Response} object containing response body as {@code List<LanguageVO>}
     */
    public static Response getAllLanguage() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_ALL_LANGUAGE_PATH);
    }
    /**
     * Fetch all Email service provider
     *
     * @return {@link Response} object containing response body as {@code List<ServiceProviderVO>}
     */
    public static Response getAllEmailServiceProvider() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_EMAIL_SERVICE_PROVIDERS_PATH);
    }
    /**
     * Fetches All SMS service provider
     *
     * @return {@link Response} object containing response body as {@code List<ServiceProviderVO>}
     */
    public static Response getAllSMSServiceProvider() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_SMS_SERVICE_PROVIDERS_PATH);
    }
    /**
     * Fetches All Chat_bot service provider
     *
     * @return {@link Response} object containing response body as {@code List<ServiceProviderVO>}
     */
    public static Response getAllChatBotServiceProvider() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_CHATBOT_SERVICE_PROVIDERS_PATH);
    }
    /**
     * Fetches All Marketing Automation service provider
     *
     * @return {@link Response} object containing response body as {@code List<ServiceProviderVO>}
     */
    public static Response getAllMarketingAutomationServiceProvider() {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_MARKETING_AUTOMATION_SERVICE_PROVIDERS_PATH);
    }
}
