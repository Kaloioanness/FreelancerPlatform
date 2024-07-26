package softuni.bg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.bg.model.dtos.JobListingDTO;
import softuni.bg.model.dtos.UserDTO;
import softuni.bg.service.JobListingService;
import softuni.bg.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/job-listings")
public class JobListingController {

    @Autowired
    private JobListingService jobListingService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllJobListings(Model model) {
        List<JobListingDTO> jobListings = jobListingService.getAllJobListings();
        model.addAttribute("jobListings", jobListings);
        return "job-listings";
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
        return "redirect:/job-listings";
    }

    @GetMapping("/edit/{id}")
    public String showEditJobListingForm(@PathVariable Long id, Model model) {
        JobListingDTO jobListing = jobListingService.getJobListingById(id);
        model.addAttribute("jobListingDTO", jobListing);
        return "edit-job-listing";
    }

    @PostMapping("/edit/{id}")
    public String editJobListing(@PathVariable Long id, @ModelAttribute("jobListingDTO") JobListingDTO jobListingDTO) {
        jobListingService.updateJobListing(id, jobListingDTO);
        return "redirect:/job-listings";
    }

    @GetMapping("/delete/{id}")
    public String deleteJobListing(@PathVariable Long id) {
        jobListingService.deleteJobListing(id);
        return "redirect:/job-listings";
    }
}
