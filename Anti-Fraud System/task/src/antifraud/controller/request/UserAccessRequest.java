package antifraud.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Accessors(chain = true)
public class UserAccessRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String operation;
}
