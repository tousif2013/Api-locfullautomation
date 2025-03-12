package in.credable.automation.service.notification;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderName;
import in.credable.automation.service.vo.notification.EmailNotificationVO;
import in.credable.automation.service.vo.notification.NotificationResponseVO;
import in.credable.automation.service.vo.notification.TemplateRequestVO;
import in.credable.automation.service.vo.notification.TemplateVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

/**
 * @author Prashant Rana
 */
public final class NotificationService {
    private NotificationService() {
    }

    /**
     * Sends email notification
     *
     * @param emailNotificationVO The request object of {@link EmailNotificationVO}
     * @return {@link Response} object containing body as {@link NotificationResponseVO}
     */
    public static Response sendEmailNotification(EmailNotificationVO emailNotificationVO) {
        return RestAssuredClient
                .withBaseUrl(EndPoint.SEND_NOTIFICATION_BASE_URL)
                .enableLoggingFilters(true, true)
                .contentType(ContentType.JSON)
                .body(emailNotificationVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.SEND_NOTIFICATION_BASE_PATH);
    }

    /**
     * Registers template for the specified vendor
     *
     * @param templateVO The request object of {@link TemplateVO}
     * @param vendorId   The vendor id
     * @param clientId   The client id
     * @return {@link Response} object containing body as {@code ResponseWO<TemplateVO>}
     */
    public static Response registerTemplate(TemplateVO templateVO, String vendorId, String clientId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.NOTIFICATION_SERVICE_BASE_PATH)
                .header(HeaderName.CLIENT_ID, clientId)
                .contentType(ContentType.JSON)
                .body(templateVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.REGISTER_TEMPLATE_PATH, vendorId);
    }

    /**
     * Fetches template for the specified vendor
     *
     * @param vendorId           The vendor id
     * @param clientId           The client id
     * @param templateCode       The template code
     * @param expectedStatusCode The expected status code
     * @return {@link Response} object containing body as {@code ResponseWO<TemplateVO>}
     */
    public static Response fetchTemplate(String vendorId, String clientId, String templateCode, StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.NOTIFICATION_SERVICE_BASE_PATH)
                .header(HeaderName.CLIENT_ID, clientId)
                .accept(ContentType.JSON)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.GET, EndPoint.FETCH_TEMPLATE_PATH, vendorId, templateCode);
    }

    /**
     * Fetches all templates for the specified vendor
     *
     * @param vendorId The vendor id
     * @param clientId The client id
     * @return {@link Response} object containing body as {@code ResponseWO<List<TemplateVO>>}
     */
    public static Response fetchTemplatesForVendor(String vendorId, String clientId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.NOTIFICATION_SERVICE_BASE_PATH)
                .header(HeaderName.CLIENT_ID, clientId)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.FETCH_TEMPLATES_PATH, vendorId);
    }

    /**
     * Updates template for the specified vendor
     *
     * @param templateRequestVO The {@link TemplateRequestVO} object
     * @param vendorId          The vendor id
     * @param clientId          The client id
     * @param templateCode      The template code
     * @return {@link Response} object containing body as {@code ResponseWO<TemplateVO>}
     */
    public static Response updateTemplate(TemplateRequestVO templateRequestVO, String vendorId, String clientId,
                                          String templateCode) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.NOTIFICATION_SERVICE_BASE_PATH)
                .header(HeaderName.CLIENT_ID, clientId)
                .contentType(ContentType.JSON)
                .body(templateRequestVO)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.PUT, EndPoint.UPDATE_TEMPLATE_PATH, vendorId, templateCode);
    }

    /**
     * Deletes template for the specified vendor
     *
     * @param templateCode The template code
     * @param vendorId     The vendor id
     * @param clientId     The client id
     * @return {@link Response} object containing body as {@code ResponseWO<String>}
     */
    public static Response deleteTemplate(String templateCode, String vendorId, String clientId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.NOTIFICATION_SERVICE_BASE_PATH)
                .header(HeaderName.CLIENT_ID, clientId)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.DELETE, EndPoint.DELETE_TEMPLATE_PATH, vendorId, templateCode);
    }
}
