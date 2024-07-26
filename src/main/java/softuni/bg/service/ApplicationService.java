package softuni.bg.service;

import softuni.bg.model.dtos.ApplicationDTO;

import java.util.List;

public interface ApplicationService {
    void applyForJob(ApplicationDTO applicationDTO);
    List<ApplicationDTO> getApplicationsForJob(Long jobListingId);
    List<ApplicationDTO> getApplicationsByFreelancer(Long freelancerId);
}

