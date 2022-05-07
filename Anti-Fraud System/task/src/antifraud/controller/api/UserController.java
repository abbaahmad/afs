package antifraud.controller.api;

import antifraud.controller.request.NewUserRequest;
import antifraud.controller.request.RoleChangeRequest;
import antifraud.controller.request.UserAccessRequest;
import antifraud.dto.UserDto;
import antifraud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody @Valid NewUserRequest userRequest){
        return userService.saveUser(userRequest);
    }

    @GetMapping("/list")
    public List<UserDto> getAll(){
        return userService.getAll();
    }

    @PutMapping("/role")
    public UserDto changeUserRole(@RequestBody @Valid RoleChangeRequest request){
        return userService.changeRole(request);
    }

    @PutMapping("/access")
    public Map<String, String> changeUserAccess(@RequestBody @Valid UserAccessRequest request){
        return userService.changeAccess(request);
    }

    @DeleteMapping("/user/{username}")
    public Map<String, String> deleteUser(@PathVariable("username") String username){
        return userService.delete(username);
    }
}
