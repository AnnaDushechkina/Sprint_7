import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import java.util.Collections;
import java.util.List;
import model.CourierData;
import model.OderResponseData;
import model.OrderData;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Check order can be get")
public class GetOrderTest {

  protected int orderId;
  protected int trackId;
  protected int courierId;
  protected String loginParam = "Valdis" + RandomStringUtils.randomAlphabetic(4);
  protected CourierData courierData;
  protected OrderData orderData;
  protected CourierApi courierApi = new CourierApi();
  protected OrderApi orderApi = new OrderApi();

  @Before
  public void beforeOrder() {
    courierData = new CourierData(loginParam, "Valdis3", "Valdis");
    courierApi.createCourier(courierData);
    ValidatableResponse responseLogin = courierApi.loginCourier(courierData);
    courierId = responseLogin.extract().body().path("id");

    orderData = new OrderData("Rafael" + RandomStringUtils.randomAlphabetic(4), "Thurtles",
                              "atorov, " + RandomStringUtils.randomNumeric(4) + " apt.", "61",
                              "+79175555513", 5, "2024-12-12",
                              "_", new String[] { "GRAY", "BLACK" });

    ValidatableResponse createOrderResponse = orderApi.createOrder(orderData);
    trackId = createOrderResponse.extract().body().path("track");

    createOrderResponse.log().all()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED)
        .body("track", is(trackId));

    ValidatableResponse responseOrder = orderApi.getOrderByTrack(trackId);
    OderResponseData orderDataByTrack = responseOrder.extract().response().getBody().as(OderResponseData.class);
    orderId = orderDataByTrack.getOrder().getId().intValue();

    ValidatableResponse responseAcceptOrder = orderApi.acceptOrderByCourier(orderId, courierId);

    responseAcceptOrder.log().all()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("ok", is(true));
  }

  @Test
  public void getOrderByCourierTest() {

    ValidatableResponse responseOrderByCourier = orderApi.getOrdersByCourier(courierId);

    List<OderResponseData> orderDataByTrack = Collections.singletonList(responseOrderByCourier.extract()
                                                                            .response().getBody()
                                                                            .as(OderResponseData.class));

    assertFalse(orderDataByTrack.isEmpty());
  }

  @After
  public void cleanUp() {
    ValidatableResponse responseLogin = courierApi.loginCourier(courierData);
    courierId = responseLogin.extract().body().path("id");

    ValidatableResponse responseDelete = courierApi.deleteCourier(courierId);

    responseDelete.log().all()
        .assertThat()
        .statusCode(HttpStatus.SC_OK);

  }
}
