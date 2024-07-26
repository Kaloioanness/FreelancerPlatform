package softuni.bg.model.entity;

import jakarta.persistence.*;
import softuni.bg.model.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "job_listings")
public class JobListing extends BaseEntity {
        @Column(nullable = false)
        private String title;
        @Column(columnDefinition = "TEXT", nullable = false)
        private String description;
        @Column(nullable = false)
        private BigDecimal budget;
        @Column(nullable = false)
        private String skillsRequired;

        @ManyToOne
        @JoinColumn(name = "client_id", nullable = false)
        private UserEntity client;
        @OneToMany(mappedBy = "jobListing")
        private List<Application> applications = new ArrayList<>();
        @Column(nullable = false)
        private LocalDateTime datePosted = LocalDateTime.now();
        //CONSTRUCTOR
        public JobListing(){}
        // GETTERS AND SETTERS!


    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
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

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
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
