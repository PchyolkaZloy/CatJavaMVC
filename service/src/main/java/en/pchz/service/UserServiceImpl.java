package en.pchz.service;

import en.pchz.common.Role;
import en.pchz.dto.RoleDto;
import en.pchz.dto.UserDto;
import en.pchz.entity.CatMaster;
import en.pchz.entity.User;
import en.pchz.exception.UserServiceException;
import en.pchz.extension.CatMasterExtension;
import en.pchz.extension.UserExtension;
import en.pchz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CatMasterService catMasterService;


    @Override
    public UserDto findUserById(Integer userId) {
        var optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserServiceException("Error! No users by this ID!");
        }

        return UserExtension.asDto(optionalUser.get());
    }

    @Override
    public void createUser(UserDto userDto) {
        var optionalUser = userRepository.findByUsernameIgnoreCase(userDto.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserServiceException("Error! User for this cat master already exists!");
        }

        CatMaster catMaster = null;
        if (userDto.getCatMasterId() != null) {
            catMaster = CatMasterExtension.asEntity(catMasterService.findCatMasterById(userDto.getCatMasterId()));
        }

        userRepository.save(
                new User(
                        userDto.getId(),
                        userDto.getUsername(),
                        userDto.getPassword(),
                        RoleDto.transferToEntity(userDto.getRole()),
                        catMaster
                ));
    }


    @Override
    public Integer findCatMasterIdByUsername(String username) {
        var optionalUser = userRepository.findByUsernameIgnoreCase(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found by this username");
        }

        Integer catMasterId = optionalUser.get().getCatMaster().getId();
        catMasterService.findCatMasterById(catMasterId);

        return catMasterId;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = userRepository.findByUsernameIgnoreCase(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found by this username");
        }

        var user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getRole()));
    }

    @Override
    public void deleteUser(Integer userId) {
        var optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserServiceException("Error! No users by this ID!");
        }

        userRepository.delete(optionalUser.get());
    }

    private Set<GrantedAuthority> getAuthorities(Role role) {
        return Stream.of(RoleDto.transferToDto(role))
                .map(rl -> new SimpleGrantedAuthority("ROLE_" + rl.name()))
                .collect(Collectors.toSet());
    }
}
