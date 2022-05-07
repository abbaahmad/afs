package antifraud.dto;

import antifraud.model.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private Role role;
}
