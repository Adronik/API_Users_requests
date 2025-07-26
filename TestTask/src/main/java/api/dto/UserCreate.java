package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreate {

    private String currency_code;
    private String email;
    private String name;

    @JsonProperty("password_change")
    private String passwordChange;
    @JsonProperty("password_repeat")
    private String passwordRepeat;

    private String surname;
    private String username;

}
