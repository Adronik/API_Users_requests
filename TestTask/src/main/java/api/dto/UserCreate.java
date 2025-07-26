package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreate {

    public String currency_code;
    public String email;
    public String name;

    @JsonProperty("password_change")
    public String passwordChange;
    @JsonProperty("password_repeat")
    public String passwordRepeat;

    public String surname;
    public String username;

}
