package apiTests;

import api.dto.UserCreate;
import api.dto.UserCreateResponse;
import api.dto.UserEmail;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static api.spec.Specifications.authSpec;
import static io.restassured.RestAssured.given;
import static java.util.Collections.sort;
import static org.testng.Assert.*;
import static utils.APIUrls.*;


public class PlayersAPI extends BaseTestSetups {

    @DataProvider(name = "userDataProvider", parallel = true)
    public Object[][] userDataProvider() {
        Object[][] users = new Object[12][1];
        for (int i = 0; i < 12; i++) {
            UserCreate user = UserCreate.builder()
                    .currency_code("USD")
                    .email(faker.internet().emailAddress())
                    .name(faker.name().firstName())
                    .passwordChange("Password1234!")
                    .passwordRepeat("Password1234!")
                    .surname(faker.name().lastName())
                    .username(faker.name().username())
                    .build();
            users[i][0] = user;
        }
        return users;
    }

    @Test
    public void verifyAccessTokenReturnedAfterLogin() {
        assertNotNull(authToken, "Access token is null");
    }

    @Test(dataProvider = "userDataProvider")
    public void verifyUserCreateAndFetch(UserCreate user) {

        UserCreateResponse createdUser = given()
                .spec(authSpec(authToken))
                .body(user)
                .when().post(USERS_CREATION_PATH)
                .then().log().ifError()
                .statusCode(201)
                .extract().as(UserCreateResponse.class);

        assertNotNull(createdUser.getId(), "ID should not be null");
        assertEquals(createdUser.getUsername(), user.getUsername(), "Username doesn't match");
        assertEquals(createdUser.getEmail(), user.getEmail(), "Email doesn't match");
        assertEquals(createdUser.getName(), user.getName(), "Name doesn't match");
        assertEquals(createdUser.getSurname(), user.getSurname(), "Surname doesn't match");

        UserEmail searchBody = UserEmail.builder()
                .email(createdUser.getEmail())
                .build();

        UserCreateResponse fetchedUser = given()
                .spec(authSpec(authToken))
                .body(searchBody)
                .when().post(GET_ONE_USER)
                .then().log().ifError()
                .statusCode(201)
                .extract().as(UserCreateResponse.class);

        assertEquals(fetchedUser.getId(), createdUser.getId(), "ID doesn't match");
        assertEquals(fetchedUser.getEmail(), createdUser.getEmail(), "Email doesn't match");
        assertEquals(fetchedUser.getCurrency_code(), createdUser.getCurrency_code(), "Currency code doesn't match");
        assertEquals(fetchedUser.getName(), createdUser.getName(), "Name doesn't match");
        assertEquals(fetchedUser.getSurname(), createdUser.getSurname(), "Surname doesn't match");
        assertEquals(fetchedUser.getUsername(), createdUser.getUsername(), "Username doesn't match");

        users.add(createdUser);
    }

    @Test(dependsOnMethods = "verifyUserCreateAndFetch")
    public void verifyUsersByNameSorting() {
        assertFalse(users.isEmpty(), "Users list is empty");

        List<String> userNames = users.stream()
                .map(UserCreateResponse::getName)
                .toList();

        List<String> expectedSortedNames = new ArrayList<>(userNames);
        sort(expectedSortedNames);

        List<String> actualSortedNames = userNames.stream()
                .sorted()
                .toList();

        assertEquals(expectedSortedNames, actualSortedNames,
                "Names are not sorted");
    }

    @Test(dependsOnMethods = "verifyUsersByNameSorting")
    public void verifyUsersDeletion() {
        List<String> userIDs = new ArrayList<>();
        List<UserCreateResponse> users = given()
                .spec(authSpec(authToken))
                .when()
                .get(GET_ALL_USERS)
                .then().log().ifError()
                .statusCode(200)
                .extract().jsonPath().getList(".", UserCreateResponse.class);

        for (UserCreateResponse user : users) {
            userIDs.add(user.getId());
        }

        for (String userID : userIDs) {
            given()
                    .spec(authSpec(authToken))
                    .when()
                    .delete(DELETE_USER + userID)
                    .then().log().all()
                    .statusCode(200);
        }

        List<UserCreateResponse> usersAfterRemoval = given()
                .spec(authSpec(authToken))
                .when()
                .get(GET_ALL_USERS)
                .then().log().ifError()
                .statusCode(200)
                .extract().jsonPath().getList(".", UserCreateResponse.class);

        assertTrue(usersAfterRemoval.isEmpty(), "Users were not deleted: " + usersAfterRemoval);

    }

}
