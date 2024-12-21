import static org.hamcrest.CoreMatchers.is;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierData;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Check courier can be created")
public class CreateCourierTest {

  protected int courierId;
  protected String loginParam = "Valdis" + RandomStringUtils.randomAlphabetic(4);
  protected CourierData courierData;
  protected CourierApi courierApi;

  @Before
  public void createCourier() {
    courierData = new CourierData(loginParam, "Valdis1", "Valdis");
    courierApi = new CourierApi();
  }

  @Test
  public void courierCreateTest() {
    ValidatableResponse response = courierApi.createCourier(courierData);

    response.log().all()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED)
        .body("ok", is(true));
  }

  @Test
  public void courierCreateScTest() {
    ValidatableResponse response = courierApi.createCourier(courierData);

    response.log().all()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);
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