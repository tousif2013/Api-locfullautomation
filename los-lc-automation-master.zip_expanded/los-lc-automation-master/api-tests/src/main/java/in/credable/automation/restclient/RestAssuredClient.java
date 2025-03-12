package in.credable.automation.restclient;

import in.credable.automation.config.ConfigFactory;
import in.credable.automation.service.header.HeaderName;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.File;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

@Log4j2
public final class RestAssuredClient {
    private static final Set<String> BLACK_LISTED_HEADERS = Set.of(HeaderName.X_CLIENT_ID, HeaderName.ORG_ID, HeaderName.CLIENT_SECRET);
    private final Writer requestWriter;
    private final Writer responseWriter;
    private final Writer errorWriter;
    private boolean isRequestLoggerAttached = false;
    private boolean isResponseLoggerAttached = false;
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    private RestAssuredClient(String baseUrl) {
        this.requestSpecification = RestAssured.given().baseUri(baseUrl);
        this.responseSpecification = this.requestSpecification.expect();
        this.requestWriter = new StringWriter();
        this.responseWriter = new StringWriter();
        this.errorWriter = new StringWriter();
    }

    public static RestAssuredClient withBaseUrl(String baseUrl) {
        return new RestAssuredClient(baseUrl);
    }

    public static RestAssuredClient withDefaultBaseUrl() {
        return new RestAssuredClient(ConfigFactory.getEnvironmentConfig().getBaseUrl());
    }

    public static RestAssuredClient withDefaultConfigurations() {
        RestAssuredClient restAssuredClient = withDefaultBaseUrl();
        return restAssuredClient.enableLoggingFilters(true, true);
    }

    public RestAssuredClient enableLoggingFilters(boolean enableRequestLogging, boolean enableResponseLogging) {
        if (enableRequestLogging) {
            addRequestLoggingFilter();
        }
        if (enableResponseLogging) {
            addResponseLoggingFilter();
        }
        addErrorLoggingFilter();
        return this;
    }

    public RestAssuredClient basePath(String basePath) {
        this.requestSpecification = this.requestSpecification.basePath(basePath);
        return this;
    }

    public RestAssuredClient bearerToken(String token) {
        this.requestSpecification = this.requestSpecification.header(HeaderName.AUTHORIZATION, "Bearer " + token);
        return this;
    }

    public RestAssuredClient headers(Map<String, ?> headers) {
        this.requestSpecification = this.requestSpecification.headers(headers);
        return this;
    }

    public RestAssuredClient header(String headerName, String headerValue) {
        this.requestSpecification = this.requestSpecification.header(headerName, headerValue);
        return this;
    }

    public RestAssuredClient contentType(ContentType contentType) {
        this.requestSpecification = this.requestSpecification.contentType(contentType);
        return this;
    }

    public RestAssuredClient queryParam(String parameterName, Object... parameterValues) {
        this.requestSpecification = this.requestSpecification.queryParam(parameterName, parameterValues);
        return this;
    }

    public RestAssuredClient queryParams(Map<String, Object> map) {
        requestSpecification = this.requestSpecification.queryParams(map);
        return this;
    }

    public RestAssuredClient body(Object object) {
        this.requestSpecification = this.requestSpecification.body(object);
        return this;
    }

    public RestAssuredClient multipart(String parameterName, String contentBody, String mimeType) {
        this.requestSpecification = this.requestSpecification.multiPart(parameterName, contentBody, mimeType);
        return this;
    }

    public RestAssuredClient multipart(String parameterName, File file, String mimeType) {
        this.requestSpecification = this.requestSpecification.multiPart(parameterName, file, mimeType);
        return this;
    }

    public RestAssuredClient accept(ContentType contentType) {
        this.requestSpecification = this.requestSpecification.accept(contentType);
        return this;
    }

    public RestAssuredClient expectStatusCode(int statusCode) {
        this.responseSpecification = this.responseSpecification.statusCode(statusCode);
        return this;
    }

    public Response request(Method method, String path, Object... pathParams) {
        Response response;
        try {
            response = RestAssured.given(this.requestSpecification, this.responseSpecification)
                    .request(method, path, pathParams);
        } catch (Exception e) {
            log.error("Exception while sending request", e);
            throw e;
        } finally {
            logRequestDetails();
            logResponseDetails();
            logErrorDetails();
        }
        return response;
    }

    private void addRequestLoggingFilter() {
        this.requestSpecification = this.requestSpecification.filter(new RequestLoggingFilter(
                LogDetail.ALL,
                true,
                getPrintStream(this.requestWriter),
                true,
                BLACK_LISTED_HEADERS
        ));
        this.isRequestLoggerAttached = true;
    }

    private void addResponseLoggingFilter() {
        this.requestSpecification = this.requestSpecification.filter(new ResponseLoggingFilter(getPrintStream(this.responseWriter)));
        this.isResponseLoggerAttached = true;
    }

    private void addErrorLoggingFilter() {
        this.requestSpecification = this.requestSpecification.filter(new ErrorLoggingFilter(getPrintStream(this.errorWriter)));
    }

    @SneakyThrows
    private static PrintStream getPrintStream(Writer writer) {
        return new PrintStream(WriterOutputStream.builder()
                .setWriter(writer)
                .setCharset(Charset.defaultCharset())
                .setWriteImmediately(true)
                .get());
    }

    @SneakyThrows
    private void logRequestDetails() {
        if (this.isRequestLoggerAttached) {
            log.debug("Request details:\n{}", requestWriter);
            requestWriter.flush();
        }
    }

    @SneakyThrows
    private void logResponseDetails() {
        if (this.isResponseLoggerAttached) {
            log.debug("Response details:\n{}", responseWriter);
            responseWriter.flush();
        }
    }

    @SneakyThrows
    private void logErrorDetails() {
        if (!((StringWriter) errorWriter).getBuffer().isEmpty()) {
            log.error("Error details:\n{}", errorWriter);
        }
        errorWriter.flush();
    }


}
