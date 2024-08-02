package softuni.bg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.bg.model.entity.Application;
import softuni.bg.model.enums.JobListingStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByJobListingId(Long id);
    List<Application> findByJobListingId(Long jobId);
    boolean existsByJobListingIdAndFreelancerId(Long jobListingId, Long freelancerId);
    Collection<Object> findByFreelancerId(Long freelancerId);
}

