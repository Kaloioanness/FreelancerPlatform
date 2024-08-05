package softuni.bg.model.dtos;

import softuni.bg.model.entity.Contract;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.UserEntity;

import java.time.LocalDate;

public class ReviewDTO {

    private Long id;
    private String comment;
    private Integer rating;
    private UserEntity reviewer;
    private UserEntity reviewee;
    private Contract contract;
    private LocalDate dateReviewed;

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public UserEntity getReviewer() {
        return reviewer;
    }

    public void setReviewer(UserEntity reviewer) {
        this.reviewer = reviewer;
    }

    public UserEntity getReviewee() {
        return reviewee;
    }

    public void setReviewee(UserEntity reviewee) {
        this.reviewee = reviewee;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public LocalDate getDateReviewed() {
        return dateReviewed;
    }

    public void setDateReviewed(LocalDate dateReviewed) {
        this.dateReviewed = dateReviewed;
    }
}
