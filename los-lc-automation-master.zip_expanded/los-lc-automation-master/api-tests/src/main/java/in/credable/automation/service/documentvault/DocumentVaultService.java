package in.credable.automation.service.documentvault;

import in.credable.automation.commons.utils.FileUtil;
import in.credable.automation.commons.utils.SerializationUtil;
import in.credable.automation.enums.MimeType;
import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.documentvault.DocumentUploadRequestVO;
import in.credable.automation.utils.EndPoint;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.io.File;
import java.util.Map;

public final class DocumentVaultService {
    private DocumentVaultService() {
    }

    public static Response uploadDocument(DocumentUploadRequestVO documentUploadRequestVO, File document) {
        String data = SerializationUtil.serialize(documentUploadRequestVO);
        File dataFile = FileUtil.convertStringToTempFile("data.json", data);
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.DOCUMENT_VAULT_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getDocumentVaultUploadRequestHeaders())
                .contentType(ContentType.MULTIPART)
                .multipart("data", dataFile, MimeType.JSON.getMimeType())
                .multipart("document", document, MimeType.EXCEL.getMimeType())
                .accept(ContentType.JSON)
                .expectStatusCode(200)
                .request(Method.POST, EndPoint.DOCUMENT_VAULT_UPLOAD_PATH);
    }

    public static Response downloadDocument(String relativeStoragePath) {
        return RestAssuredClient
                .withDefaultBaseUrl()
                .enableLoggingFilters(true, false)
                .basePath(EndPoint.DOCUMENT_VAULT_SERVICE_BASE_PATH)
                .contentType(ContentType.JSON)
                .queryParam("fileType", "file")
                .body(Map.of("relativeStoragePath", relativeStoragePath))
                .accept(ContentType.JSON)
                .expectStatusCode(200)
                .request(Method.POST, EndPoint.DOCUMENT_VAULT_DOWNLOAD_PATH);
    }

    public static Response getUploadStatus(String transactionId) {
        return RestAssuredClient
                .withDefaultBaseUrl()
                .enableLoggingFilters(true, true)
                .basePath(EndPoint.DOCUMENT_VAULT_SERVICE_BASE_PATH)
                .accept(ContentType.JSON)
                .expectStatusCode(200)
                .request(Method.GET, EndPoint.UPLOAD_STATUS_PATH, transactionId);
    }
}
