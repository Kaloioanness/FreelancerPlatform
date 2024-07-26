package softuni.bg.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.bg.model.entity.Contract;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.model.enums.ContractStatus;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findByFreelancer(UserEntity freelancer);

    List<Contract> findByClient(UserEntity client);

    // Additional methods
}

