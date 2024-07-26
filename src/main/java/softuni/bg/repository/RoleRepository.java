package softuni.bg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.bg.model.entity.Role;
import softuni.bg.model.enums.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

}
