package softuni.bg.model.entity;

import jakarta.persistence.*;

import softuni.bg.model.BaseEntity;
import softuni.bg.model.enums.JobListingStatus;


import java.time.LocalDateTime;

@Entity
@Table(name = "applications")

public class Application extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "job_listing_id", nullable = false)
    private JobListing jobListing;
    @ManyToOne
    @JoinColumn(name = "user_entity_id", nullable = false)
    private UserEntity freelancer;


    @Column(columnDefinition = "TEXT")
    private String coverLetter;
    @Column(nullable = false)
    private String status;


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn = LocalDateTime.now();


    public Application(){}
    //getters and setters

    public String getStatus() {
        return status;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserEntity getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(UserEntity userEntity) {
        this.freelancer = userEntity;
    }

    public JobListing getJobListing() {
        return jobListing;
    }

    public void setJobListing(JobListing jobListing) {
        this.jobListing = jobListing;
    }
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}