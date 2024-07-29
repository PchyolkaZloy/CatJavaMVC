package en.pchz.service;

import en.pchz.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto findUserById(Integer userId);

    void createUser(UserDto userDto);

    void deleteUser(Integer userId);

    Integer findCatMasterIdByUsername(String username);
}
