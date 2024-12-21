import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

@DisplayName("Check courier can be login")
public class LoginCourierTest {

    protected int courierId;
    protected String loginParam = "Valdis" + RandomStringUtils.randomAlphabetic(4);
    protected CourierData courierData;
    protected CourierApi courierApi;


    @Before
    public void createCourier() {
        courierData = new CourierData(loginParam, "Valdis2");
        courierApi = new CourierApi();
        courierApi.createCourier(courierData);
        System.out.println("courierData - " + courierData);

    }

    @Test
    public void loginTest() {
        ValidatableResponse responseLogin = courierApi.loginCourier(courierData);
        courierId = responseLogin.extract().body().path("id");

        responseLogin.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(courierId));

    }

    @Test
    public void getErrorLogin() {
        CourierData courierDataNew = new CourierData(loginParam, "1");

        ValidatableResponse responseLogin = courierApi.loginCourier(courierDataNew);

        responseLogin.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));

    }

    @Test
    public void getEmptyLogin() {
        CourierData courierDataNew = new CourierData(loginParam, "");

        ValidatableResponse responseLogin = courierApi.loginCourier(courierDataNew);

        responseLogin.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));

    }

    @Test
    public void getFakeLogin() {
        CourierData courierDataNew = new CourierData("Petya", "Petya");

        ValidatableResponse responseLogin = courierApi.loginCourier(courierDataNew);

        responseLogin.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));

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
