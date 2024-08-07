package softuni.bg.model.dtos.info;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import softuni.bg.model.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JobListingInfoDTO {
    private Long id;
    @Size(min = 5,max = 40, message = "Title length must be between 5 and 40 characters!")
    @NotNull
    private String title;
    @Size(min = 10,max = 150, message = "Description length must be between 10 and 150 characters!")
    @NotNull
    private String description;
    @Positive(message = "Budget must be a positive number")
    private BigDecimal budget;
    @Size(min = 5,max = 50, message = "Skills required length must be between 5 and 50 characters!")
    @NotNull
    private String skillsRequired;
    private UserEntity client;
    private LocalDateTime datePosted;

    private int applicationsCount;
    //getters and setters

    public int getApplicationsCount() {
        return applicationsCount;
    }

    public void setApplicationsCount(int applicationsCount) {
        this.applicationsCount = applicationsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public UserEntity getClient() {
        return client;
    }

    public void setClient(UserEntity client) {
        this.client = client;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }
}
