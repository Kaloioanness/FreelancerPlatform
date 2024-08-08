package softuni.bg.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.bg.model.dtos.ReviewDTO;
import softuni.bg.model.dtos.UserDTO;
import softuni.bg.model.entity.Contract;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.service.ContractService;
import softuni.bg.service.ReviewService;
import softuni.bg.service.UserService;
import softuni.bg.service.impl.JobListingServiceImpl;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/my-reviews")
    public String getMyReviews(Principal principal,Model model) {
        UserDTO userByUsername = userService.findUserByUsername(principal.getName());
        Optional<UserEntity> byId = userService.findById(userByUsername.getId());
        if (byId.isEmpty()){
            return "review-list";
        }
        List<ReviewDTO> myReviews = reviewService.findByReviewer(byId.get());
        model.addAttribute("myReviews", myReviews);
        return "my-reviews";
    }
    @PostMapping("my-reviews/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews/my-reviews";
    }
    @GetMapping("/my-reviews/edit/{id}")
    public String showEditReviewForm(@PathVariable("id") Long reviewId, Principal principal, Model model) {
        ReviewDTO reviewDTO = reviewService.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Long userId = userService.findUserByUsername(principal.getName()).getId();

        if (reviewDTO.getReviewer().getId() != userId) {
            return "redirect:/access-denied";
        }

        model.addAttribute("reviewDTO", reviewDTO);
        return "edit-review";
    }
    @PostMapping("/my-reviews/edit/{id}")
    public String updateReview(@PathVariable("id") Long reviewId,
                               @ModelAttribute("reviewDTO") ReviewDTO reviewDTO) {
        ReviewDTO existingReview = reviewService.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Update the existing review
        existingReview.setRating(reviewDTO.getRating());
        existingReview.setComment(reviewDTO.getComment());
        existingReview.setDateReviewed(LocalDate.now());

        reviewService.updateReview(existingReview);

        return "redirect:/reviews/my-reviews";
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
        model.addAttribute("reviewDTO", new ReviewDTO());
        return "review-list";
    }


    @GetMapping("/create")
    public String showCreateReviewForm(@RequestParam("contractId") Long contractId, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        Long reviewerId = userService.findUserByUsername(principal.getName()).getId();

        if (reviewService.hasAlreadyReviewed(contractId, reviewerId)) {
            redirectAttributes.addFlashAttribute("error", "You have already reviewed this contract.");
            return "redirect:/reviews";
        }

        ReviewDTO reviewDTO = new ReviewDTO();
        Contract contract = contractService.getContractById(contractId);
        reviewDTO.setContract(contract);

        UserEntity loggedUser = userService.findById(reviewerId).orElseThrow(() -> new RuntimeException("Logged in user not found"));
        reviewDTO.setReviewer(loggedUser);
        reviewDTO.setReviewee(contractService.returnOtherUser(contract, loggedUser));

        model.addAttribute("reviewDTO", reviewDTO);
        return "create-review";
    }

    @PostMapping("/create")
    public String createReview(@RequestParam("contractId") Long contractId, @ModelAttribute("reviewDTO") ReviewDTO reviewDTO,
                               RedirectAttributes redirectAttributes) {
        // Check if the review already exists
        Long reviewerId = reviewDTO.getReviewer().getId();
        if (reviewService.hasAlreadyReviewed(contractId, reviewerId)) {
            redirectAttributes.addFlashAttribute("error", "You have already reviewed this contract.");
            return "redirect:/reviews";
        }

        // Proceed with review creation
        reviewDTO.setContract(contractService.getContractById(contractId));
        reviewDTO.setDateReviewed(LocalDate.now());
        reviewService.createReview(reviewDTO);
        return "redirect:/reviews";
    }
}
