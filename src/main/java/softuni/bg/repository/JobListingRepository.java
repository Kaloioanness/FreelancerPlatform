package softuni.bg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.UserEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobListingRepository extends JpaRepository<JobListing, Long> {

    Optional<List<JobListing>> findByClient(UserEntity client);
    Optional<List<JobListing>> findByClientIsNot(UserEntity client);
    List<JobListing> findByTitleContainingIgnoreCase(String keyword);

    List<JobListing> findBySkillsRequiredContainingIgnoreCase(String skill);

    List<JobListing> findByBudgetBetween(BigDecimal minBudget, BigDecimal maxBudget);
    Optional<JobListing> findJobListingById(long id);
}
