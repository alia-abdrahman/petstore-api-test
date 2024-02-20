import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class TC01_CreateUserTest {


    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);

    }

    // Creates list of users with given input array
    @Test
    public void createUserTest(){


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

       given()
                .contentType("application/json")
                .body(requestBody)
                .post("/user/createWithArray")
                .then()
                .statusCode(200);


    }

    @Test
    public void createInvalidUserTest(){


        // Provide invalid user data (e.g. extra comma in request body)
        String invalidRequestBody = "[{" +
                "    \"id\": 12," +
                "    \"username\": \"username\"," +
                "    \"firstName\": \"firstname\"," +
                "    \"lastName\": \"lastname\"," +
                "    \"email\": \"name@email.com\"," +
                "    \"password\": \"abc123\"," +
                "    \"phone\": \"123456789\"," +
                "    \"userStatus\": 0\"," +
                "  }]";


        Response response = given()
                .contentType("application/json")
                .body(invalidRequestBody)
                .when()
                .post("/user/createWithArray")
                        .then()
                        .statusCode(500)
                        .extract().response();

        System.out.println("Response : " + response.asString());


    }

    // Get user by user name
    @Test
    public void getUserDetailsTest(){


       String username = given()
                .pathParam("username", "username")
                .when()
                .get("/user/{username}")
                        .then()
                        .statusCode(200)
                        .extract().jsonPath().getString("username");
       // After extracting the username from the response, an assertion is added
        // to ensure that the retrieved username matches the expected username.
        //This assertion ensures that the test validates not only the status code
        // but also the correctness of the retrieved username.
        assertEquals(username, "username");


    }

    // step to retrieve non existing user details
    @Test
    public void getUserInvalidTest(){

        Response response = given()
                .pathParam("username", " ")
                .when()
                .get("/user/{username}")
                        .then()
                        .statusCode(404)
                        .extract().response();

        System.out.println("Response : " + response.asString());

    }

}
