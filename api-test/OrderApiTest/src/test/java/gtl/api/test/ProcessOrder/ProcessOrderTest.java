package gtl.api.test.ProcessOrder;

import com.google.gson.JsonObject;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import gtl.api.constants.Endpoint;
import gtl.api.helper.FileHelper;
import gtl.api.client.RestAssuredClient;

public class ProcessOrderTest {
    private RestAssuredClient restAssuredClient = RestAssuredClient.getInstance();

    @Test(description = "Test process order success")
    @Epic("API Test")
    @Story("Process Order")
    public void testProcessOrderSuccess() {
        FileHelper fileHelper = new FileHelper();
        JsonObject jsonBody = fileHelper.loadJsonFile("data/ProcessOrder.json");

        RequestSpecification requestSpec = restAssuredClient.getRequestSpec();
        Response response =
            given()
                .spec(requestSpec)
                .body(jsonBody.toString())
            .when()
                .post(Endpoint.PROCESS_ORDER);

        response.then()
            .assertThat()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Order.json"))
            .body("order_id", equalTo(jsonBody.get("order_id").getAsInt()))
            .body("order_status", equalTo("Processing"))
            .body("special_order", equalTo(jsonBody.get("special_order").getAsBoolean()));

        // Assert timestamp
        long responseTimestamp = Long.parseLong(response.jsonPath().get("last_updated_timestamp").toString());
        long requestTimestamp = Long.parseLong(jsonBody.get("last_updated_timestamp").getAsString());
        assertThat(responseTimestamp, greaterThan(requestTimestamp));
    }

    @DataProvider(name = "missing_property_body")
    public Object[][] missingProperty() {
        return new Object[][] {
            {"order_id"},
            {"order_description"},
            {"order_status"},
            {"last_updated_timestamp"},
            {"special_order"}
        };
    }

    @Test(
        description = "Test process order failed missing property",
        dataProvider = "missing_property_body"
    )
    @Epic("API Test")
    @Story("Process Order")
    public void testProcessOrderMissingProperty(String propertyName) {
        FileHelper fileHelper = new FileHelper();
        JsonObject jsonBody = fileHelper.loadJsonFile("data/ProcessOrder.json");
        jsonBody.remove(propertyName);

        RequestSpecification requestSpec = restAssuredClient.getRequestSpec();
        Response response =
            given()
                .spec(requestSpec)
                .body(jsonBody.toString())
                .when()
                .post(Endpoint.PROCESS_ORDER);

        response.then()
            .assertThat()
            .statusCode(400);
    }

    @DataProvider(name = "invalid_type_body")
    public Object[][] invalidType() {
        return new Object[][] {
            {"order_id", "123647162"},
            {"order_description", true},
            {"order_status", 1},
            {"last_updated_timestamp", 47726172},
            {"special_order", "false"}
        };
    }

    @Test(
        description = "Test process order failed invalid type data",
        dataProvider = "invalid_type_body"
    )
    @Epic("API Test")
    @Story("Process Order")
    public void testProcessOrderInvalidType(String propertyName, Object value) {
        FileHelper fileHelper = new FileHelper();
        JsonObject jsonBody = fileHelper.loadJsonFile("data/ProcessOrder.json");
        Class<?> valueClass = value.getClass();
        if (valueClass == String.class) jsonBody.addProperty(propertyName, String.valueOf(value));
        if (valueClass == Boolean.class) jsonBody.addProperty(propertyName, Boolean.parseBoolean(value.toString()));
        if (valueClass == Integer.class) jsonBody.addProperty(propertyName, Integer.parseInt(value.toString()));

        RequestSpecification requestSpec = restAssuredClient.getRequestSpec();
        Response response =
            given()
                .spec(requestSpec)
                .body(jsonBody.toString())
                .when()
                .post(Endpoint.PROCESS_ORDER);

        response.then()
            .assertThat()
            .statusCode(400);
    }
}
