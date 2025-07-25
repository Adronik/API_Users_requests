package Utils;

import java.io.IOException;
import java.util.Properties;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

public class UserProvider {

    private static final Properties userProp = new Properties();

    public UserProvider() {
        try (var inputStream = UserProvider.class.getResourceAsStream("/user.properties")) {
            userProp.load(inputStream);
        } catch (IOException e) {
            getStackTrace(e);
        }
    }

    public static String get(String key) {
        return userProp.getProperty(key);
    }

}
