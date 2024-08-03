package softuni.bg.service;

import softuni.bg.model.dtos.ApplicationDTO;
import softuni.bg.model.dtos.ApplicationInfoDTO;

import java.util.List;

public interface ApplicationService {
    void applyForJob(ApplicationDTO applicationDTO);
    List<ApplicationInfoDTO> getApplicationsForJob(Long jobListingId);
//    List<ApplicationDTO> getApplicationsByFreelancer(Long freelancerId);
    List<ApplicationInfoDTO> getApplicationsByFreelancer(Long freelancerId);

    void deleteApplicationById(Long id);
}

