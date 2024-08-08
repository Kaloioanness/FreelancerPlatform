package softuni.bg.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.bg.model.dtos.JobListingDTO;
import softuni.bg.model.dtos.info.JobListingInfoDTO;
import softuni.bg.service.JobListingService;
import softuni.bg.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/client-job-listings")
public class EditJobListingController {

    private final JobListingService jobListingService;
    private final UserService userService;


    public EditJobListingController(JobListingService jobListingService, UserService userService) {
        this.jobListingService = jobListingService;
        this.userService = userService;
    }

    @ModelAttribute("jobListingEdit")
    public JobListingInfoDTO jobListingDTO(){
        return new JobListingInfoDTO();
    }
    @GetMapping("/edit/{id}")
    public String showEditJobListingForm(@PathVariable Long id, Model model) {
        JobListingInfoDTO jobListing = jobListingService.getJobListingById(id);
        model.addAttribute("jobListingEdit", jobListing);
        return "edit-job-listing";
    }

    @PostMapping("/edit/{id}")
    public String editJobListing( @PathVariable Long id, @Valid JobListingDTO jobListingDTO,
                                 BindingResult bindingResult, RedirectAttributes rda,
                                 Principal principal) {

        if (bindingResult.hasErrors()) {
            rda.addFlashAttribute("jobListingEdit", jobListingDTO);
            rda.addFlashAttribute("org.springframework.validation.BindingResult.jobListingEdit", bindingResult);
            return "redirect:/client-job-listings/edit/{id}";
        }
        Long clientId = userService.findUserByUsername(principal.getName()).getId();
        jobListingService.updateJobListing(id, jobListingDTO, clientId);
        return "redirect:/client-job-listings";
    }
}
