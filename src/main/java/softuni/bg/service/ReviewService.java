package softuni.bg.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.bg.model.dtos.ReviewDTO;
import softuni.bg.model.entity.Contract;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.Review;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.repository.ContractRepository;
import softuni.bg.repository.ReviewRepository;
import softuni.bg.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    public ReviewService(ReviewRepository reviewRepository, ModelMapper modelMapper, ContractRepository contractRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
    }
    public Optional<ReviewDTO> findById(Long id) {
        return reviewRepository.findById(id).map(this::convertToDTO);
    }
    public void updateReview(ReviewDTO reviewDTO) {
        Review reviewEntity = convertToEntity(reviewDTO);
        reviewRepository.save(reviewEntity);
    }

    public List<ReviewDTO> findByReviewer(UserEntity reviewer) {
        List<Review> reviews = reviewRepository.findByReviewer(reviewer);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> findByReviewee(UserEntity reviewee) {
        List<Review> reviews = reviewRepository.findByReviewee(reviewee);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public void createReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setDateReviewed(reviewDTO.getDateReviewed());

        UserEntity reviewer = userRepository.findById(reviewDTO.getReviewer().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        review.setReviewer(reviewer);

        UserEntity reviewee = userRepository.findById(reviewDTO.getReviewee().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        review.setReviewee(reviewee);
        review.setContract(reviewDTO.getContract());
        reviewRepository.save(review);

        // Update ratings and review counts
        reviewer.setTotalRatingGiven(reviewer.getTotalRatingGiven() + reviewDTO.getRating());
        reviewer.setNumberOfReviewsGiven(reviewer.getNumberOfReviewsGiven() + 1);
        userRepository.save(reviewer);

        reviewee.setTotalRatingReceived(reviewee.getTotalRatingReceived() + reviewDTO.getRating());
        reviewee.setNumberOfReviewsReceived(reviewee.getNumberOfReviewsReceived() + 1);
        userRepository.save(reviewee);
    }

    public Optional<ReviewDTO> getReviewById(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        return reviewOptional.map(this::convertToDTO);
    }

    public List<ReviewDTO> findAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public boolean hasAlreadyReviewed(Long contractId, Long reviewerId) {
        return reviewRepository.existsByContractIdAndReviewerId(contractId, reviewerId);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    private ReviewDTO convertToDTO(Review review) {
        return modelMapper.map(review, ReviewDTO.class);
    }

    private Review convertToEntity(ReviewDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }
}
