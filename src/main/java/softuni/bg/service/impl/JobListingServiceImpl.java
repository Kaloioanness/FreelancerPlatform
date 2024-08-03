package softuni.bg.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import softuni.bg.model.dtos.JobListingDTO;
import softuni.bg.model.dtos.JobListingInfoDTO;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.repository.JobListingRepository;
import softuni.bg.repository.UserRepository;
import softuni.bg.service.JobListingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobListingServiceImpl implements JobListingService {

    private final JobListingRepository jobListingRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public JobListingServiceImpl(JobListingRepository jobListingRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.jobListingRepository = jobListingRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public JobListingDTO createJobListing(JobListingDTO jobListingDTO) {
        JobListing jobListing = modelMapper.map(jobListingDTO, JobListing.class);
        UserEntity client = userRepository.findById(jobListingDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        jobListing.setClient(client);
        jobListing.setDatePosted(LocalDateTime.now());
        JobListing savedJobListing = jobListingRepository.save(jobListing);
        return modelMapper.map(savedJobListing, JobListingDTO.class);
    }

    @Override
    public void updateJobListing(Long id, JobListingDTO jobListingDTO, Long clientId) {
        JobListing existingJobListing = jobListingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job listing not found"));

        UserEntity client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        modelMapper.map(jobListingDTO, existingJobListing);
        existingJobListing.setDatePosted(LocalDateTime.now());
        existingJobListing.setClient(client);
        jobListingRepository.save(existingJobListing);
    }

    @Override
    public JobListingInfoDTO getJobListingById(Long id) {
        JobListing jobListing = jobListingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job listing not found"));
        return modelMapper.map(jobListing, JobListingInfoDTO.class);
    }

    @Override
    public List<JobListingInfoDTO> getAllJobListings() {
        return jobListingRepository.findAll().stream()
                .map(jobListing -> modelMapper.map(jobListing, JobListingInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteJobListing(Long id) {
//        JobListing jobListing = jobListingRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Job listing not found"));
        jobListingRepository.deleteById(id);
    }

    @Override
    public UserEntity getCurrentUser(String Invalid_client_ID) {
        String username = getCurrentUsername();
        System.out.println("Username: " + username);

        UserEntity client = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(Invalid_client_ID));
        return client;
    }
    @Override
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @Override
    public List<JobListingInfoDTO> getJobListingsByClient(Long clientId) {
        return jobListingRepository.findByClientId(clientId).stream()
                .map(jobListing -> modelMapper.map(jobListing, JobListingInfoDTO.class))
                .collect(Collectors.toList());
    }

}
