package apiTests;

import api.dto.UserAuth;
import api.dto.UserCreateResponse;
import io.restassured.http.ContentType;
import net.datafaker.Faker;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.LoginUserProvider;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.restassured.RestAssured.given;
import static utils.APIUrls.BASE_URL;
import static utils.APIUrls.LOGIN_PATH;

public class BaseTestSetups {

    protected static LoginUserProvider user;
    protected static String authToken;
    protected final Faker faker = new Faker();
    protected static List<UserCreateResponse> users = new CopyOnWriteArrayList<>();

    @BeforeClass()
    public void testPreparation(){
        user = new LoginUserProvider();

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

    @AfterClass
    public void testCleanup(){
        users.clear();
    }

}
