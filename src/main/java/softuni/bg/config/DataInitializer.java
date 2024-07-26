package softuni.bg.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import softuni.bg.model.entity.Role;
import softuni.bg.model.enums.RoleName;
import softuni.bg.repository.RoleRepository;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
