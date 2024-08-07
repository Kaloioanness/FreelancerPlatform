package softuni.bg.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import softuni.bg.model.dtos.ReviewDTO;
import softuni.bg.model.entity.Contract;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.service.ContractService;
import softuni.bg.service.ReviewService;
import softuni.bg.service.UserService;
import softuni.bg.service.impl.JobListingServiceImpl;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final JobListingServiceImpl jobListingServiceImpl;
    private final ContractService contractService;


    public ReviewController(ReviewService reviewService, UserService userService, JobListingServiceImpl jobListingServiceImpl, ContractService contractService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.jobListingServiceImpl = jobListingServiceImpl;
        this.contractService = contractService;
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long reviewId) {
        return reviewService.getReviewById(reviewId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public String getAllReviews(Model model) {
        List<ReviewDTO> allReviews = reviewService.findAllReviews();
        model.addAttribute("allReviews", allReviews);
        return "review-list";
    }

//    @PostMapping
//    public String createReview(@ModelAttribute("reviewDTO") @Valid ReviewDTO reviewDTO, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            List<ReviewDTO> reviews = reviewService.findAllReviews();
//            model.addAttribute("reviews", reviews);
//            return "review-list"; // Return to the same page with validation errors
//        }
//
////        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
//        return "redirect:/reviews"; // Redirect to the review list page after successful creation
//    }



    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reviewer")
    public String findByReviewer(@RequestParam Long reviewerId, Model model) {
        UserEntity reviewer = userService.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("User with id " + reviewerId + " not found"));
        List<ReviewDTO> reviews = reviewService.findByReviewer(reviewer);
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewDTO", new ReviewDTO()); // Add an empty ReviewDTO for the form
        return "review-list";
    }

    @GetMapping("/reviewee")
    public String findByReviewee(@RequestParam Long revieweeId, Model model) {
        UserEntity reviewee = userService.findById(revieweeId)
                .orElseThrow(() -> new RuntimeException("User with id " + revieweeId + " not found"));
        List<ReviewDTO> reviews = reviewService.findByReviewee(reviewee);
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewDTO", new ReviewDTO()); // Add an empty ReviewDTO for the form
        return "review-list";
    }


    @GetMapping("/create")
    public String showCreateReviewForm(@RequestParam("contractId") Long contractId, Principal principal, Model model) {
        ReviewDTO reviewDTO = new ReviewDTO();
        Contract contract = contractService.getContractById(contractId);
        reviewDTO.setContract(contract);

        // Set reviewer and reviewee based on your logic
        Long id = userService.findUserByUsername(principal.getName()).getId();
        UserEntity loggedUser = userService.findById(id).orElseThrow(() -> new RuntimeException("Logged in user not found"));
        reviewDTO.setReviewer(loggedUser);
        reviewDTO.setReviewee(contractService.returnOtherUser(contract, loggedUser));

        model.addAttribute("reviewDTO", reviewDTO);
        return "create-review";
    }

    @PostMapping("/create")
    public String createReview(@RequestParam("contractId") Long contractId, @ModelAttribute("reviewDTO") ReviewDTO reviewDTO) {
        reviewDTO.setContract(contractService.getContractById(contractId));
        reviewDTO.setDateReviewed(LocalDate.now());
        reviewService.createReview(reviewDTO);
        return "redirect:/reviews";
    }
}
