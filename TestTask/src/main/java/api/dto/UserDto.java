package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String id;
    private String email;
    private String name;
    private String surname;
    private String role;
    private String position;
    private String status;
    private Boolean isReport;
    private String comment;
    private String createBy;
    private String report;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("created_at")
    private String createdAt;

    private Boolean feedback;

    @JsonProperty("finished_at")
    private String finishedAt;

    private String linkedin;
    private String city;
    private String jira;

}

