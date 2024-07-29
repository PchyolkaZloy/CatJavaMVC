package en.pchz.extension;


import en.pchz.dto.RoleDto;
import en.pchz.dto.UserDto;
import en.pchz.entity.User;

public class UserExtension {
    public static UserDto asDto(User user) {
        if (user == null) {
            return UserDto.builder().build();
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(RoleDto.transferToDto(user.getRole()))
                .catMasterId(user.getCatMaster() == null ? null : user.getCatMaster().getId())
                .build();
    }
}
