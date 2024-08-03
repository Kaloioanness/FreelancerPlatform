package softuni.bg.controller;

import org.springframework.http.ResponseEntity;
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

    @GetMapping("/create")
    public String showCreateJobListingForm(Model model) {
        model.addAttribute("jobListingDTO", new JobListingDTO());
        return "create-job-listing";
    }

    @PostMapping("/create")
    public String createJobListing(@ModelAttribute("jobListingDTO") JobListingDTO jobListingDTO, Principal principal, Model model) {
        String username = principal.getName();
        System.out.println("Logged in user username: " + username);

        UserDTO userDTO = userService.findUserByUsername(username);
        if (userDTO == null) {
            model.addAttribute("error", "User not found with username: " + username);
            return "error";
        }

        Long clientId = userDTO.getId();
        jobListingDTO.setClientId(clientId);
        jobListingService.createJobListing(jobListingDTO);
        return "redirect:/client-job-listings";
    }

    @GetMapping("/edit/{id}")
    public String showEditJobListingForm(@PathVariable Long id, Model model) {
        JobListingInfoDTO jobListing = jobListingService.getJobListingById(id);
        model.addAttribute("jobListingDTO", jobListing);
        return "edit-job-listing";
    }

    @PostMapping("/edit/{id}")
    public String editJobListing(@PathVariable Long id, @ModelAttribute("jobListingDTO") JobListingDTO jobListingDTO, Principal principal) {
        Long clientId = userService.findUserByUsername(principal.getName()).getId();
        jobListingService.updateJobListing(id, jobListingDTO, clientId);
        return "redirect:/client-job-listings";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJobListing(@PathVariable Long id) {
        jobListingService.deleteJobListing(id);
        return ResponseEntity.ok().build();
    }
}
