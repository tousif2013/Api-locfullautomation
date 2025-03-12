package in.credable.automation.service.dataingestion;

import in.credable.automation.commons.utils.SerializationUtil;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.enums.MimeType;
import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.vo.dataingestion.DataIngestionFileUploadRequestDataVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.RandomDataGenerator;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class DataIngestionService {
    private DataIngestionService() {
    }

    public static Response uploadBorrowers(DataIngestionFileUploadRequestDataVO dataIngestionFileUploadRequestDataVO,
                                           File uploadExcelFile) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("transaction_id", RandomDataGenerator.getUuid());
        headers.put("client_id", ConfigFactory.getEnvironmentConfig().getClientIdForKeycloakOrganization());

        String dataJson = SerializationUtil.serialize(dataIngestionFileUploadRequestDataVO);
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.DATA_INGESTION_SERVICE_BASE_PATH)
                .contentType(ContentType.MULTIPART)
                .headers(headers)
                .multipart("data", dataJson, MimeType.JSON.getMimeType())
                .multipart("file", uploadExcelFile, MimeType.EXCEL.getMimeType())
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.DATA_INGESTION_SERVICE_UPLOAD_BORROWERS_PATH);
    }
}
