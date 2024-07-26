package softuni.bg.model.entity;

import jakarta.persistence.*;
import softuni.bg.model.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String comment;
    @Column(nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    private UserEntity reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewee_id", nullable = false)
    private UserEntity reviewee;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Column(nullable = false)
    private LocalDateTime dateReviewed;

    public Review (){}
    //GETTERS AND SETTERS


    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
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



    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDateReviewed() {
        return dateReviewed;
    }

    public void setDateReviewed(LocalDateTime dateReviewed) {
        this.dateReviewed = dateReviewed;
    }

    // Getters and setters, constructors, toString()
}
