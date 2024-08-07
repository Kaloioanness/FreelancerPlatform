package softuni.bg.model.entity;

import jakarta.persistence.*;
import softuni.bg.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity  extends BaseEntity {


    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<JobListing> jobListings = new ArrayList<>();

    @OneToMany(mappedBy = "freelancer")
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<Contract> clientContracts = new ArrayList<>();

    @OneToMany(mappedBy = "freelancer")
    private List<Contract> freelancerContracts = new ArrayList<>();

    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviewsGiven = new ArrayList<>();

    @OneToMany(mappedBy = "reviewee")
    private List<Review> reviewsReceived = new ArrayList<>();



    // New fields for ratings
    @Column(nullable = false)
    private int totalRatingGiven = 0;

    @Column(nullable = false)
    private int totalRatingReceived = 0;

    @Column(nullable = false)
    private int numberOfReviewsGiven = 0;

    @Column(nullable = false)
    private int numberOfReviewsReceived = 0;


    @Column(name = "image_url")
    private String imageUrl; // profile pic
    public UserEntity   (){}

    // Getters and setters


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTotalRatingGiven() {
        return totalRatingGiven;
    }

    public void setTotalRatingGiven(int totalRatingGiven) {
        this.totalRatingGiven = totalRatingGiven;
    }

    public int getTotalRatingReceived() {
        return totalRatingReceived;
    }

    public void setTotalRatingReceived(int totalRatingReceived) {
        this.totalRatingReceived = totalRatingReceived;
    }

    public int getNumberOfReviewsGiven() {
        return numberOfReviewsGiven;
    }

    public void setNumberOfReviewsGiven(int numberOfReviewsGiven) {
        this.numberOfReviewsGiven = numberOfReviewsGiven;
    }

    public int getNumberOfReviewsReceived() {
        return numberOfReviewsReceived;
    }

    public void setNumberOfReviewsReceived(int numberOfReviewsReceived) {
        this.numberOfReviewsReceived = numberOfReviewsReceived;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<Review> getReviewsGiven() {
        return reviewsGiven;
    }

    public void setReviewsGiven(List<Review> reviewsGiven) {
        this.reviewsGiven = reviewsGiven;
    }

    public List<Review> getReviewsReceived() {
        return reviewsReceived;
    }

    public void setReviewsReceived(List<Review> reviewsReceived) {
        this.reviewsReceived = reviewsReceived;
    }

    public List<Contract> getFreelancerContracts() {
        return freelancerContracts;
    }

    public void setFreelancerContracts(List<Contract> freelancerContracts) {
        this.freelancerContracts = freelancerContracts;
    }

    public List<Contract> getClientContracts() {
        return clientContracts;
    }

    public void setClientContracts(List<Contract> clientContracts) {
        this.clientContracts = clientContracts;
    }

    public List<JobListing> getJobListings() {
        return jobListings;
    }

    public void setJobListings(List<JobListing> jobListings) {
        this.jobListings = jobListings;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAverageRatingGiven() {
        return numberOfReviewsGiven > 0 ? (double) totalRatingGiven / numberOfReviewsGiven : 0.0;
    }

    public double getAverageRatingReceived() {
        return numberOfReviewsReceived > 0 ? (double) totalRatingReceived / numberOfReviewsReceived : 0.0;
    }

}
