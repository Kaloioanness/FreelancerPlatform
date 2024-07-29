package softuni.bg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.bg.model.dtos.ApplicationDTO;
import softuni.bg.service.ApplicationService;
import softuni.bg.service.JobListingService;
import softuni.bg.service.UserService;
import softuni.bg.service.impl.JobListingServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {


    private final ApplicationService applicationService;
    private final JobListingService jobListingService;


    private final UserService userService;

    public ApplicationController(ApplicationService applicationService, JobListingService jobListingService, UserService userService) {
        this.applicationService = applicationService;
        this.jobListingService = jobListingService;
        this.userService = userService;
    }

    @GetMapping("/apply/{jobListingId}")
    public String showApplicationForm(@PathVariable Long jobListingId, Model model) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setJobListingId(jobListingId);
        model.addAttribute("applicationDTO", applicationDTO);
        return "apply";
    }

    @PostMapping("/apply")
    public String applyForJob(@ModelAttribute("applicationDTO") ApplicationDTO applicationDTO, Principal principal) {
        Long freelancerId = userService.findUserByUsername(principal.getName()).getId();
        applicationDTO.setFreelancerId(freelancerId);
        applicationService.applyForJob(applicationDTO);
        return "redirect:/job-listings";
    }

    @GetMapping("/job/{jobListingId}")
    public String getApplicationsForJob(@PathVariable Long jobListingId, Model model) {
        List<ApplicationDTO> applications = applicationService.getApplicationsForJob(jobListingId);
        model.addAttribute("applications", applications);
        return "applications";
    }

    @GetMapping("/freelancer")
    public String getApplicationsByFreelancer(Principal principal, Model model) {
        Long freelancerId = userService.findUserByUsername(principal.getName()).getId();
        List<ApplicationDTO> applications = applicationService.getApplicationsByFreelancer(freelancerId);
        model.addAttribute("applications", applications);
//        this.jobListingService.getJobListingById(applications.getFirst().getJobListingId());
        return "freelancer-applications";
    }
}
