package softuni.bg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.bg.model.dtos.JobListingDTO;
import softuni.bg.model.dtos.JobListingInfoDTO;
import softuni.bg.model.dtos.UserDTO;
import softuni.bg.service.JobListingService;
import softuni.bg.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/job-listings")
public class JobListingController {
    private final JobListingService jobListingService;
    public JobListingController(JobListingService jobListingService) {
        this.jobListingService = jobListingService;
    }

    @GetMapping
    public String getAllJobListings(Model model) {
        List<JobListingInfoDTO> jobListings = jobListingService.getAllJobListings();
        model.addAttribute("jobListings", jobListings);
        return "job-listings";
    }

    @GetMapping("/details/{id}")
    public String jobListingDetails(@PathVariable Long id, Model model, JobListingDTO jobListingDTO) {
        JobListingInfoDTO jobListingById = jobListingService.getJobListingById(id);
        model.addAttribute("jobListingById",jobListingById);
        return "job-listing-details";
    }
}
