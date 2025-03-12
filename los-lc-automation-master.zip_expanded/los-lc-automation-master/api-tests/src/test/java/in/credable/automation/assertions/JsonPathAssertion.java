package in.credable.automation.assertions;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

public class JsonPathAssertion {
    private final JsonPath jsonPathObject;

    private JsonPathAssertion(Response response) {
        this.jsonPathObject = response.jsonPath();
    }

    public static JsonPathAssertion assertThat(Response response) {
        return new JsonPathAssertion(response);
    }

    public JsonPathAssertion assertString(String jsonPath, String expectedValue) {
        Assertions.assertThat(jsonPathObject.getString(jsonPath)).isEqualTo(expectedValue);
        return this;
    }

    public JsonPathAssertion assertNotNull(String jsonPath) {
        Assertions.assertThat(jsonPathObject.getString(jsonPath)).isNotNull();
        return this;
    }

    public JsonPathAssertion assertListIsNotEmpty(String jsonPath) {
        Assertions.assertThat(jsonPathObject.getList(jsonPath))
                .as(() -> "Given JSON path expression list is empty")
                .isNotEmpty();
        return this;
    }

}
