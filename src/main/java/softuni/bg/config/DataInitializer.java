package softuni.bg.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import softuni.bg.model.entity.Role;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.model.enums.RoleName;
import softuni.bg.repository.RoleRepository;
import softuni.bg.repository.UserRepository;

import java.util.Optional;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initRoles() {
        return args -> initializeRoles();

    }

    @Transactional
    public void initializeRoles() {
        addRoleIfNotExists(RoleName.ADMIN);
        addRoleIfNotExists(RoleName.CLIENT);
        addRoleIfNotExists(RoleName.FREELANCER);
        addAdmin();
    }
    @Transactional
    public void addAdmin(){
        if (userRepository.count() == 0){
            UserEntity user = new UserEntity();
            user.setEmail("admin@admin");
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("notadmin"));
            Optional<Role> byName = this.roleRepository.findByName(RoleName.ADMIN);
            if (byName.isEmpty()){
                return;
            }
            user.getRoles().add(byName.get());
            userRepository.save(user);
        }
    }

    private void addRoleIfNotExists(RoleName roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(roleName));
            logger.info("Role {} has been added to the database.", roleName);
        } else {
            logger.info("Role {} already exists in the database.", roleName);
        }
    }
}
