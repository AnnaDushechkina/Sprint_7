import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.CourierData;

import static io.restassured.RestAssured.given;

public class CourierApi extends RestApi {
    public static final String CREATE_COURIER_URI = "api/v1/courier";
    public static final String LOGIN_COURIER_URI = "api/v1/courier/login";
    public static final String DELETE_COURIER_URI = "/api/v1/courier/{courierId}";

    @Step("Create courier")
    public ValidatableResponse createCourier(CourierData courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_URI)
                .then();
    }

    @Step("Login courier")
    public ValidatableResponse loginCourier(CourierData courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_URI)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourier(Integer courierId) {
        return given()
                .spec(requestSpecification())
                .delete(DELETE_COURIER_URI, courierId)
                .then();
    }
}