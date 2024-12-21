import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.OrderCancelData;
import model.OrderData;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {

    public static final String ORDER_URI = "/api/v1/orders";
    public static final String ORDER_CANCEL_URI = "/api/v1/orders/cancel";
    public static final String ORDER_ACCEPT_URI = "/api/v1/orders/accept/{trackId}?courierId={courierId}";
    public static final String ORDER_GET_URI = "/api/v1/orders/track?t={trackId}";
    public static final String ORDER_GET_BY_COURIER_URI = "/api/v1/orders?courierId={courierId}";

    @Step("Create order")
    public ValidatableResponse createOrder(OrderData order) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(ORDER_URI)
                .then();
    }

    @Step("Get order by track")
    public ValidatableResponse getOrderByTrack(Integer trackId) {
        return given()
                .spec(requestSpecification())
                .and()
                .when()
                .get(ORDER_GET_URI, trackId)
                .then();
    }

    @Step("Accept order by courer")
    public ValidatableResponse acceptOrderByCourier(Integer trackId, Integer courierId) {
        return given()
                .spec(requestSpecification())
                .and()
                .when()
                .put(ORDER_ACCEPT_URI, trackId, courierId)
                .then();
    }

    @Step("Get orders by courier")
    public ValidatableResponse getOrdersByCourier(Integer courierId) {
        return given()
                .spec(requestSpecification())
                .and()
                .when()
                .get(ORDER_GET_BY_COURIER_URI, courierId)
                .then();
    }

}