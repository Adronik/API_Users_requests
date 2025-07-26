package apiTests;

import api.dto.UserCreate;
import api.dto.UserCreateResponse;
import api.dto.UserEmail;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static api.spec.Specifications.authSpec;
import static java.lang.String.valueOf;
import static io.restassured.RestAssured.given;
import static utils.APIUrls.*;


public class PlayersAPI extends BaseTestSetups {

    @DataProvider(name = "userDataProvider", parallel = true)
    public Object[][] userDataProvider() {
        Object[][] users = new Object[12][1];
        for (int i = 0; i < 12; i++) {
            String uniqueSuffix = valueOf(System.currentTimeMillis() + i);
            UserCreate user = UserCreate.builder()
                    .currency_code("USD")
                    .email("user" + uniqueSuffix + "@test.com")
                    .name("Name" + i)
                    .passwordChange("Password1234!")
                    .passwordRepeat("Password1234!")
                    .surname("Surname" + i)
                    .username("user" + uniqueSuffix)
                    .build();
            users[i][0] = user;
        }
        return users;
    }

    @Test
    public void verifyAccessTokenReturnedAfterLogin() {
        Assert.assertNotNull(authToken, "Access token is null");
    }

    @Test(dataProvider = "userDataProvider")
    public void verifyUserRegistration(UserCreate user) {
        UserCreateResponse response = given()
                .spec(authSpec(authToken))
                .body(user)
                .when()
                .post(USERS_CREATION_PATH)
                .then().log().ifError()
                .statusCode(201)
                .extract()
                .as(UserCreateResponse.class);

        Assert.assertEquals(response.getUsername(), user.getUsername(), "Wrong username");
        Assert.assertEquals(response.getEmail(), user.getEmail(), "Wrong email");
        Assert.assertEquals(response.getName(), user.getName(), "Wrong name");
        Assert.assertEquals(response.getSurname(), user.getSurname(), "Wrong surname");
    }

    @Test(dependsOnMethods = "verifyUserRegistration")
    public void verifyOneUserData() {
        List<UserCreateResponse> users = given()
                .spec(authSpec(authToken))
                .get(GET_ALL_USERS)
                .then().log().ifError()
                .extract().body().jsonPath().getList(".", UserCreateResponse.class);

        String email = users.get(0).getEmail();

        UserEmail body = UserEmail.builder()
                .email(email)
                .build();

        UserCreateResponse response = given()
                .spec(authSpec(authToken))
                .body(body)
                .when()
                .post(GET_ONE_USER)
                .then().log().all()
                .statusCode(201)
                .extract().as(UserCreateResponse.class);

        Assert.assertEquals(response.getEmail(), email, "Email should match");
    }

}
