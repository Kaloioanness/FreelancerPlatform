package softuni.bg.service;

import softuni.bg.model.dtos.JobListingDTO;
import softuni.bg.model.dtos.JobListingInfoDTO;
import softuni.bg.model.entity.UserEntity;

import java.util.List;

public interface JobListingService {
    JobListingDTO createJobListing(JobListingDTO jobListingDTO);
    void updateJobListing(Long id, JobListingDTO jobListingDTO, Long clientId);
    JobListingInfoDTO getJobListingById(Long id);
    List<JobListingInfoDTO> getAllJobListings();

    void deleteJobListing(Long id);
    UserEntity getCurrentUser(String Invalid_client_ID);
    String getCurrentUsername();

    List<JobListingInfoDTO> getJobListingsByClient(Long clientId);
}
