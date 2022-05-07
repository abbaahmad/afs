package antifraud.service;

import antifraud.controller.request.NewUserRequest;
import antifraud.controller.request.RoleChangeRequest;
import antifraud.controller.request.UserAccessRequest;
import antifraud.dto.UserDto;
import antifraud.dto.mapper.UserMapper;
import antifraud.model.Role;
import antifraud.model.User;
import antifraud.model.UserDetailsImpl;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist."));

        return new UserDetailsImpl(user);
    }

    public UserDto saveUser(NewUserRequest userRequest) {
        if(userRepository.existsByUsername(userRequest.getUsername().toLowerCase())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User exist!");
        }

        User newUser = mapper.toUser(userRequest);

        if(userRepository.findAll().size() == 0)
            newUser.setRole(Role.ADMINISTRATOR).setEnabled(true);
        else
            newUser.setRole(Role.MERCHANT).setEnabled(false);

        User savedUser = userRepository.save(newUser);

        return mapper.toUserDto(savedUser);
    }

    public List<UserDto> getAll() {
        return  userRepository.findAll()
                .stream().map(mapper::toUserDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public Map<String, String> delete(String username) {
        User user = userRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist."));

        userRepository.delete(user);

        return Map.of("username", username, "status", "Deleted successfully!");
    }

    public UserDto changeRole(RoleChangeRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!isRole(request.getRole()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Role requestRole = Role.valueOf(request.getRole().toUpperCase());
        List<Role> allowedRoles = List.of(Role.MERCHANT, Role.SUPPORT);

        if(!allowedRoles.contains(requestRole) || !allowedRoles.contains(user.getRole()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if(user.getRole() == Role.valueOf(request.getRole()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        user.setRole(Role.valueOf(request.getRole()));
        User updatedUser = userRepository.save(user);

        return mapper.toUserDto(updatedUser);
    }

    public Map<String, String> changeAccess(UserAccessRequest request) {
        System.out.println(request.getOperation());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(request.getOperation().equals("LOCK")) {
            if(user.getRole() == Role.ADMINISTRATOR)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            user.setEnabled(false);
        }
        else
            user.setEnabled(true);

        userRepository.save(user);

        return Map.of("status", String.format("User %s %sed!", user.getUsername(), request.getOperation().toLowerCase()));
    }

    private boolean isRole(String role){
        for(Role r : Role.values()){
            if(r.name().equalsIgnoreCase(role)){
                return true;
            }
        }
        return false;
    }
}
