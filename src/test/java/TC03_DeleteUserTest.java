import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class TC03_DeleteUserTest {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);

    }

    @BeforeMethod(onlyForGroups = {"deleteGroup"})
    public void createUser() {
         given()
                 .contentType("application/json")
                 .body("{ \"username\": \"usernametwo\", \"password\": \"password123\" }")
                 .when()
                 .post("/user")
                 .then()
                 .statusCode(200);
    }

    // step to delete existing user
    @Test (groups = "deleteGroup")
    public void deleteUserTest(){
        String username = "usernametwo";

        Response response = given()
                .pathParam("username", username)
                .when()
                .delete("/user/{username}");

        // Check if the user was deleted successfully (status code 200) or not found (status code 404)
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200, "Expected status code 200 after deleting the user");
    }


    // step to delete non-existing user
    @Test
    public void deleteUserInvalidTest(){
        String username = "abc123";

        Response response = given()
                .pathParam("username", username)
                .when()
                .delete("/user/{username}");

        // Check if the user was not found (status code 404)
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 404, "Expected status code 404 for non-existing user");

    }
}
