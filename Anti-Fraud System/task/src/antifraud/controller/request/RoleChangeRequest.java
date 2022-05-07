package antifraud.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Accessors(chain = true)
public class RoleChangeRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String role;
}
