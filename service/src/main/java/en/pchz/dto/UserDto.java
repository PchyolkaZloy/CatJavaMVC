package en.pchz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private RoleDto role;
    private Integer catMasterId;

    public UserDto(String username, String password, RoleDto role, Integer catMasterId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.catMasterId = catMasterId;
    }
}
