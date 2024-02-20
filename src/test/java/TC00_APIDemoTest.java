import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC00_APIDemoTest {

    @Test
    void test1(){

        Response response = get("https://reqres.in/api/users?page=2");
        response.getStatusCode();

        System.out.println("Response : " + response.asString());
        System.out.println("Status code : " +response.getStatusCode());
        System.out.println("Body : " + response.getBody().asString());
        System.out.println("Time taken : " + response.getTime());
        System.out.println("Headers : " + response.getHeader("content-type"));

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    void test2(){
        given()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);

    }
}
