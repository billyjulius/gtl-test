package gtl.api.client;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RestAssuredClient {
    private static RestAssuredClient instance = null;
    private RequestSpecification requestSpec;

    private RestAssuredClient() {
        String apiHost = System.getenv("API_HOST");
        if(apiHost == null || apiHost.isEmpty()) {
            apiHost = "http://localhost:3000";
        }

        requestSpec = RestAssured.given()
            .baseUri(apiHost)
            .basePath("/api")
            .contentType("application/json");
    }

    public static RestAssuredClient getInstance() {
        if (instance == null) {
            instance = new RestAssuredClient();
        }
        return instance;
    }

    public RequestSpecification getRequestSpec() {
        return requestSpec;
    }
}
