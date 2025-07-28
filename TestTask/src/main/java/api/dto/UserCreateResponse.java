package api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateResponse {

    @JsonAlias({ "_id", "id" })
    private String id;
    private String currency_code;
    private String username;
    private String email;
    private String name;
    private String surname;
    private int __v;

}
