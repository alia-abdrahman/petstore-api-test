import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class TC02_UpdateUserTest {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);

    }

    @Test (priority = 1)
    public void updateUserTest(){

        String username = "username";

        String requestBody = "{" +
                "    \"id\": 30," +
                "    \"username\": \"username\"," +
                "    \"firstName\": \"firstname\"," +
                "    \"lastName\": \"lastname\"," +
                "    \"email\": \"name@email.com\"," +
                "    \"password\": \"abc123\"," +
                "    \"phone\": \"123456789\"," +
                "    \"userStatus\": 0" +
                "  }";

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .pathParam("username", username)
                .when()
                .put("/user/{username}")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Response : " + response.asString());

    }

    // step to retrieve existing user details
    @Test (priority = 2)
    public void getUserDetailsTest(){
        Response response = given()
                .pathParam("username", "username")
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(200)
                        .extract().response();

        System.out.println("Response : " + response.asString());
    }



    @Test (priority = 3)
    public void updateUserInvalidTest(){

        String username = "username";

        // Provide invalid user data (e.g. insert [ ] in request body)
        String requestBody = "[{" +
                "    \"id\": 12," +
                "    \"username\": \"username\"," +
                "    \"firstName\": \"firstname\"," +
                "    \"lastName\": \"lastname\"," +
                "    \"email\": \"name@email.com\"," +
                "    \"password\": \"abc123\"," +
                "    \"phone\": \"123456789\"," +
                "    \"userStatus\": 0" +
                "  }]";

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .pathParam("username", username)
                .when()
                .put("/user/{username}")
                .then()
                .statusCode(500)
                .extract().response();

        System.out.println("Response : " + response.asString());

    }
}
