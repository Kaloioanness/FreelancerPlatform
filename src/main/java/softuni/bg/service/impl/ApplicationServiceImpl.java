package softuni.bg.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.bg.model.dtos.ApplicationDTO;
import softuni.bg.model.dtos.info.ApplicationInfoDTO;
import softuni.bg.model.entity.Application;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.repository.ApplicationRepository;
import softuni.bg.repository.JobListingRepository;
import softuni.bg.repository.UserRepository;
import softuni.bg.service.ApplicationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobListingRepository jobListingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, JobListingRepository jobListingRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.applicationRepository = applicationRepository;
        this.jobListingRepository = jobListingRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void applyForJob(ApplicationDTO applicationDTO) {
        boolean alreadyApplied = applicationRepository.existsByJobListingIdAndFreelancerId(
                applicationDTO.getJobListingId(), applicationDTO.getFreelancerId());

        if (alreadyApplied) {
            throw new RuntimeException("You have already applied for this job");
        }

        Application application = modelMapper.map(applicationDTO, Application.class);

        JobListing jobListing = jobListingRepository.findById(applicationDTO.getJobListingId())
                .orElseThrow(() -> new RuntimeException("Job listing not found"));
        UserEntity freelancer = userRepository.findById(applicationDTO.getFreelancerId())
                .orElseThrow(() -> new RuntimeException("Freelancer not found"));

        application.setJobListing(jobListing);
        application.setFreelancer(freelancer);
        application.setCreatedOn(LocalDateTime.now());
        application.setStatus("Pending");
        applicationRepository.save(application);
    }

    @Override
    public List<ApplicationInfoDTO> getApplicationsForJob(Long jobListingId) {
        return applicationRepository.findByJobListingId(jobListingId).stream()
                .map(application -> modelMapper.map(application, ApplicationInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationInfoDTO> getApplicationsByFreelancer(Long freelancerId) {
        return applicationRepository.findByFreelancerId(freelancerId).stream()
                .map(application -> modelMapper.map(application, ApplicationInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteApplicationById(Long id) {
        applicationRepository.deleteById(id);
    }

}




