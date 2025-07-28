package utils;

import java.io.IOException;
import java.util.Properties;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

public class LoginUserProvider {

    private static final Properties userProp = new Properties();

    public LoginUserProvider() {
        try (var inputStream = LoginUserProvider.class.getResourceAsStream("/user.properties")) {
            userProp.load(inputStream);
        } catch (IOException e) {
            getStackTrace(e);
        }
    }

    public String get(String key) {
        return userProp.getProperty(key);
    }

}
