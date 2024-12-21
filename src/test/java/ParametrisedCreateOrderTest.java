import io.restassured.response.ValidatableResponse;
import model.OrderData;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class ParametrisedCreateOrderTest {

    protected int orderId;
    protected OrderApi orderApi = new OrderApi();

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Number rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] colour;

    public ParametrisedCreateOrderTest(String firstName, String lastName, String address, String metroStation,
                                       String phone, Number rentTime, String deliveryDate, String comment, String[] colour) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colour = colour;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"Leonardo_" + RandomStringUtils.randomAlphabetic(4), "Thurtles", "surege, 12 apt.", "61", "+79175555517",
                        5, "2024-12-22", "_", new String[]{"GRAY", "BLACK"}},
                {"Leonardo_" + RandomStringUtils.randomAlphabetic(4), "Thurtles", "surege, 13 apt.", "61", "+79175555518",
                        5, "2024-12-23", "_", new String[]{"GRAY"}},
                {"Leonardo_" + RandomStringUtils.randomAlphabetic(4), "Thurtles", "surege, 14 apt.", "61", "+79175555517",
                        5, "2024-12-24", "_", null}
        };
    }

    @Test
    public void orderCreateTwoColorTest() {

        ValidatableResponse response = orderApi.createOrder(new OrderData(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, colour));

        orderId = response.extract().body().path("track");

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", is(orderId));
    }

}