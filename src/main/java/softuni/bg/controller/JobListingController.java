package softuni.bg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.bg.model.dtos.JobListingDTO;
import softuni.bg.model.dtos.info.JobListingInfoDTO;
import softuni.bg.service.JobListingService;

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
    public String jobListingDetails(@PathVariable Long id, Model model) {
        JobListingInfoDTO jobListingById = jobListingService.getJobListingById(id);
        model.addAttribute("jobListingById",jobListingById);
        return "job-listing-details";
    }
}
