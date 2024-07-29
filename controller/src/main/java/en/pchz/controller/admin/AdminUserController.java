package en.pchz.controller.admin;

import en.pchz.common.request.UserRequest;
import en.pchz.dto.RoleDto;
import en.pchz.dto.UserDto;
import en.pchz.exception.CatMasterServiceException;
import en.pchz.exception.UserServiceException;
import en.pchz.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/userMenu")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable("id") Integer userId, HttpServletResponse response) {
        try {
            response.setStatus(HttpStatus.OK.value());
            return userService.findUserById(userId);
        } catch (CatMasterServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());

            return null;
        }
    }

    @PostMapping("/create")
    public void createUser(@RequestBody UserRequest request, HttpServletResponse response) {
        if (request.username() != null && request.password() != null && request.role() != null) {
            response.setStatus(HttpStatus.CREATED.value());
            String encodedPassword = "{bcrypt}" + new BCryptPasswordEncoder().encode(request.password());

            userService.createUser(
                    new UserDto(
                            request.username(),
                            encodedPassword,
                            RoleDto.fromString(request.role()),
                            request.catMasterId()));
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Integer userId, HttpServletResponse response) {
        try {
            userService.deleteUser(userId);
            response.setStatus(HttpStatus.OK.value());
        } catch (UserServiceException exception) {
            System.out.println(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}
