package softuni.bg.service;

import softuni.bg.model.dtos.JobListingDTO;
import softuni.bg.model.dtos.JobListingInfoDTO;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.UserEntity;

import java.math.BigDecimal;
import java.util.List;

public interface JobListingService {
    JobListingDTO createJobListing(JobListingDTO jobListingDTO);
    JobListingDTO updateJobListing(Long id, JobListingDTO jobListingDTO);
    JobListingInfoDTO getJobListingById(Long id);
    List<JobListingInfoDTO> getAllJobListings();
    void deleteJobListing(Long id);
    UserEntity getCurrentUser(String Invalid_client_ID);
    String getCurrentUsername();
}
