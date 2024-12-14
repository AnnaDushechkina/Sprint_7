import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static org.hamcrest.CoreMatchers.is;

@DisplayName("Check courier can be created - negative test")
public class CourierNegativeTest {

    protected String loginParam = "ValdisIvanovich1";
    protected CourierData courierData;
    protected CourierApi courierApi;

    @Before
    public void createCourer() {
        courierData = new CourierData(loginParam, "Valdis1886", "Valdis");
        courierApi = new CourierApi();
    }

    @Test
    public void courierNotNullValueTest() {

        CourierData courierDataNullPassword = new CourierData(loginParam, "", "Valdis");

        ValidatableResponse response = courierApi.createCourier(courierDataNullPassword);

        response.log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createDoubleCourierTest() {

        CourierData courierDataNew = new CourierData(loginParam, "passwordValdis87654321");
        courierApi.createCourier(courierData);
        ValidatableResponse responseNew = courierApi.createCourier(courierDataNew);

        responseNew.log().all()
                .statusCode(HTTP_CONFLICT)
                .and()
                .assertThat()
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void courierCreateLoginDoubleTest() {

        CourierData courierDataLombokNew = new CourierData(loginParam, "passwordValdis1234534");

        courierApi.createCourier(courierData);
        ValidatableResponse responseNew = courierApi.createCourier(courierDataLombokNew);

        responseNew.log().all()
                .statusCode(HTTP_CONFLICT)
                .and()
                .assertThat()
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

}
