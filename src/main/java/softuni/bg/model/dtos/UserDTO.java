package softuni.bg.model.dtos;

import jakarta.persistence.Column;
import softuni.bg.model.enums.RoleName;
import java.util.List;
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<RoleName> roles;
    private int totalRatingGiven;
    private String imageUrl;

    private int totalRatingReceived;

    private int numberOfReviewsGiven;
    private int numberOfReviewsReceived;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<RoleName> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleName> roles) {
        this.roles = roles;
    }
    public double getAverageRatingGiven() {
        return numberOfReviewsGiven > 0 ? (double) totalRatingGiven / numberOfReviewsGiven : 0.0;
    }

    public double getAverageRatingReceived() {
        return numberOfReviewsReceived > 0 ? (double) totalRatingReceived / numberOfReviewsReceived : 0.0;
    }
}
