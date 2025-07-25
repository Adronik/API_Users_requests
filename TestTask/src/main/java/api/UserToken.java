package api;

public class UserToken {

    private String accessToken;

    public UserToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
