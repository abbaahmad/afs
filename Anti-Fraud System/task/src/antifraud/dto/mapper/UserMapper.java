package antifraud.dto.mapper;

import antifraud.controller.request.NewUserRequest;
import antifraud.dto.UserDto;
import antifraud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    PasswordEncoder encoder;

    public UserDto toUserDto(User user){
        return new UserDto()
                .setId(user.getId())
                .setName(user.getName())
                .setRole(user.getRole())
                .setUsername(user.getUsername());
    }

    public User toUser(NewUserRequest userRequest){
        return new User()
                .setUsername(userRequest.getUsername().toLowerCase())
                .setName(userRequest.getName())
                .setPassword(encoder.encode(userRequest.getPassword()));
    }
}
