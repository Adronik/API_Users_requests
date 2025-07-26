package apiTests;

import api.dto.AuthResponse;
import api.dto.UserAuth;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.APIUrls.LOGIN_PATH;
import static io.restassured.RestAssured.given;


public class PlayersAPI extends BaseTestSetups {

    @Test
    public void verifyAccessTokenReturnedAfterLogin() {

        UserAuth body = UserAuth.builder()
                .email(user.get("email"))
                .password(user.get("password"))
                .build();

        AuthResponse response = given()
                .body(body)
                .when()
                .post(LOGIN_PATH)
                .then().log().ifError()
                .statusCode(201)
                .extract()
                .as(AuthResponse.class);

        Assert.assertNotNull(response.getAccessToken());

    }

}
