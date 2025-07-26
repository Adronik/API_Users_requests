package api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateResponse {

    private String id;
    private String currency_code;
    private String username;
    private String email;
    private String name;
    private String surname;

}
