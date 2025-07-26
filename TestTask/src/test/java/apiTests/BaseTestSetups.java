package apiTests;

import api.dto.UserAuth;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import utils.UserProvider;

import static io.restassured.RestAssured.given;
import static utils.APIUrls.BASE_URL;
import static utils.APIUrls.LOGIN_PATH;

public class BaseTestSetups {

    public UserProvider user;
    protected static String authToken;

    @BeforeClass()
    public void testPreparation(){
        user = new UserProvider();

        UserAuth authBody = UserAuth.builder()
                .email(user.get("email"))
                .password(user.get("password"))
                .build();

        authToken = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(authBody)
                .when()
                .post(LOGIN_PATH)
                .then().log().ifError()
                .statusCode(201)
                .extract()
                .path("accessToken");
    }

}
