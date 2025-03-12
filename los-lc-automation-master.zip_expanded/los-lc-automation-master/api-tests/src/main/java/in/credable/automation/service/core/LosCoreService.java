package in.credable.automation.service.core;

import in.credable.automation.commons.utils.SerializationUtil;
import in.credable.automation.enums.MimeType;
import in.credable.automation.restclient.RestAssuredClient;
import in.credable.automation.service.header.HeaderProvider;
import in.credable.automation.service.vo.core.FileUploadRequestDataVO;
import in.credable.automation.service.vo.core.TemplateFileDownloadRequestVO;
import in.credable.automation.utils.EndPoint;
import in.credable.automation.utils.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.io.File;

public final class LosCoreService {
    private LosCoreService() {
    }

    public static Response downloadTemplate(TemplateFileDownloadRequestVO templateFileDownloadRequestVO) {
        return downloadTemplate(templateFileDownloadRequestVO, StatusCode.OK);
    }

    public static Response downloadTemplate(TemplateFileDownloadRequestVO templateFileDownloadRequestVO,
                                            StatusCode expectedStatusCode) {
        return RestAssuredClient
                .withDefaultBaseUrl()
                .enableLoggingFilters(true, false)
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getLosCoreHeaders())
                .contentType(ContentType.JSON)
                .body(templateFileDownloadRequestVO)
                .expectStatusCode(expectedStatusCode.getStatusCode())
                .request(Method.POST, EndPoint.DOWNLOAD_TEMPLATE_PATH);
    }

    public static Response uploadBorrowers(String accessToken, FileUploadRequestDataVO fileUploadRequestDataVO,
                                           File uploadFile) {
        String dataJson = SerializationUtil.serialize(fileUploadRequestDataVO);
        return RestAssuredClient
                .withDefaultConfigurations()
                .basePath(EndPoint.LOS_CORE_SERVICE_BASE_PATH)
                .headers(HeaderProvider.getLosCoreHeaders())
                .contentType(ContentType.MULTIPART)
                .bearerToken(accessToken)
                .multipart("data", dataJson, MimeType.JSON.getMimeType())
                .multipart("file", uploadFile, MimeType.EXCEL.getMimeType())
                .accept(ContentType.JSON)
                .expectStatusCode(StatusCode.OK.getStatusCode())
                .request(Method.POST, EndPoint.UPLOAD_BORROWERS_PATH);
    }

}
