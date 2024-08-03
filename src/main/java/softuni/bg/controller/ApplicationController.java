package softuni.bg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.bg.model.dtos.ApplicationDTO;
import softuni.bg.model.dtos.ApplicationInfoDTO;
import softuni.bg.service.ApplicationService;
import softuni.bg.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {


    private final ApplicationService applicationService;

    private final UserService userService;

    public ApplicationController(ApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
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
        List<ApplicationInfoDTO> applicationInfoDTOS = applicationService.getApplicationsForJob(jobListingId);
        model.addAttribute("applicationInfoDTOS", applicationInfoDTOS);
        return "applications";
    }

    @GetMapping("/freelancer")
    public String getApplicationsByFreelancer(Principal principal, Model model) {
        Long freelancerId = userService.findUserByUsername(principal.getName()).getId();
        List<ApplicationInfoDTO> applications = applicationService.getApplicationsByFreelancer(freelancerId);
        model.addAttribute("applications", applications);
//        jobListingService.getJobListingById(applications.getFirst().getJobListingId());
        return "freelancer-applications";
    }
    @PostMapping("/delete/{id}")
    public String deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplicationById(id);
        return "redirect:/applications/freelancer";
    }

}
