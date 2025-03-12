package in.credable.automation.service.client;

import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.enums.NotificationModeEnum;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.List;

public final class NotificationService {
    private NotificationService() {
    }

    public static Response addNotificationModesForClient(Long clientId, List<NotificationModeEnum> notificationModes) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(notificationModes.stream().map(NotificationModeEnum::getNotificationModeId).toList())
                .accept(ContentType.TEXT)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.ADD_NOTIFICATION_MODES_FOR_CLIENT_PATH, clientId);
    }

    public static Response getNotificationModesForClient(Long clientId) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.GET, EndPoint.GET_NOTIFICATION_MODES_FOR_CLIENT_PATH, clientId);
    }

    public static Response deleteNotificationModeForClient(Long clientId, List<NotificationModeEnum> notificationModes) {
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .body(notificationModes.stream().map(NotificationModeEnum::getNotificationModeId).toList())
                .accept(ContentType.TEXT)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.DELETE_NOTIFICATION_MODES_FOR_CLIENT_PATH, clientId);
    }
}
