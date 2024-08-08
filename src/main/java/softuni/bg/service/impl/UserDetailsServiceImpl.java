package softuni.bg.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import softuni.bg.model.FreelanceUserDetails;
import softuni.bg.model.entity.Role;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.model.enums.RoleName;
import softuni.bg.repository.UserRepository;


public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .map(UserDetailsServiceImpl::map)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    private static UserDetails map(UserEntity userEntity) {

        return new FreelanceUserDetails(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getRoles().stream().map(Role::getName).map(UserDetailsServiceImpl::map).toList(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }

    private static GrantedAuthority map(RoleName role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role.name()
        );
    }
}