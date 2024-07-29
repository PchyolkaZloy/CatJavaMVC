package en.pchz.dto;

import en.pchz.common.Role;
import org.springframework.security.core.GrantedAuthority;

public enum RoleDto implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

    public static RoleDto transferToDto(Role role) {
        for (RoleDto dto : RoleDto.values()) {
            if (role.toString().equals(dto.toString())) {
                return dto;
            }
        }
        return USER;
    }

    public static Role transferToEntity(RoleDto roleDto) {
        for (Role entityRole : Role.values()) {
            if (roleDto.toString().equals(entityRole.toString())) {
                return entityRole;
            }
        }
        return Role.USER;
    }

    public static RoleDto fromString(String colorString) {
        try {
            return RoleDto.valueOf(colorString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
