package softuni.bg.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.bg.model.dtos.JobListingDTO;
import softuni.bg.model.dtos.info.JobListingInfoDTO;
import softuni.bg.model.dtos.UserDTO;
import softuni.bg.service.JobListingService;
import softuni.bg.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/client-job-listings")
public class ClientJobListingsController {

    private final JobListingService jobListingService;
    private final UserService userService;

    public ClientJobListingsController(JobListingService jobListingService, UserService userService) {
        this.jobListingService = jobListingService;
        this.userService = userService;
    }

    @GetMapping
    public String getMyJobListings(Principal principal, Model model) {
        Long clientId = userService.findUserByUsername(principal.getName()).getId();
        List<JobListingInfoDTO> clientJobListings = jobListingService.getJobListingsByClient(clientId);
        model.addAttribute("clientJobListings", clientJobListings);
        return "client-job-listings";
    }

    @ModelAttribute("jobListingData")
    public JobListingDTO jobListingDTO(){
        return new JobListingDTO();
    }

    @GetMapping("/create")
    public String showCreateJobListingForm() {
        return "create-job-listing";
    }

    @PostMapping("/create")
    public String createJobListing(@Valid @ModelAttribute("jobListingData") JobListingDTO jobListingDTO,
                                   BindingResult bindingResult, RedirectAttributes rda,
                                   Principal principal, Model model) {

        String username = principal.getName();
        UserDTO userDTO = userService.findUserByUsername(username);
        if (userDTO == null) {
            model.addAttribute("error", "User not found with username: " + username);
            return "error";
        }
        if (bindingResult.hasErrors()) {
            rda.addFlashAttribute("jobListingData", jobListingDTO);
            rda.addFlashAttribute("org.springframework.validation.BindingResult.jobListingData", bindingResult);
            return "redirect:/client-job-listings/create";
        }

        Long clientId = userDTO.getId();
        jobListingDTO.setClientId(clientId);
        jobListingService.createJobListing(jobListingDTO);
        return "redirect:/client-job-listings/create?success=true";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJobListing(@PathVariable Long id) {
        jobListingService.deleteJobListing(id);
        return ResponseEntity.ok().build();
    }
}
