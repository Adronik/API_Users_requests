package api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthResponse {
    private UserDto user;
    private String accessToken;
}
