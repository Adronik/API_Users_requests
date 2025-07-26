package api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateResponse {

    public int id;
    public String username;
    public String email;
    public String name;
    public String surname;

}
