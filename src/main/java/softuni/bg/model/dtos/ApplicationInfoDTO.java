package softuni.bg.model.dtos;

import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.UserEntity;

import java.time.LocalDateTime;

public class ApplicationInfoDTO {
    private Long id;

    private JobListing jobListing;
    private UserEntity freelancer;
    private String coverLetter;
    private String status;
    private LocalDateTime createdOn;


    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobListing getJobListing() {
        return jobListing;
    }

    public void setJobListing(JobListing jobListing) {
        this.jobListing = jobListing;
    }

    public UserEntity getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(UserEntity freelancer) {
        this.freelancer = freelancer;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
