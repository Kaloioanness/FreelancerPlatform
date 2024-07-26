package softuni.bg.model.entity;

import jakarta.persistence.*;
import softuni.bg.model.BaseEntity;
import softuni.bg.model.enums.ContractStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contracts")
public class Contract extends BaseEntity {

    private String terms;

    private String status;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private UserEntity client;
    @ManyToOne
    @JoinColumn(name = "freelancer_id", nullable = false)
    private UserEntity freelancer;
    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    // GETTERS AND SETTERS
    public Contract (){}


    public Application getApplication() {
        return application;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public void setApplication(Application application) {
        this.application = application;
    }



    public UserEntity getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(UserEntity freelancer) {
        this.freelancer = freelancer;
    }

    public UserEntity getClient() {
        return client;
    }

    public void setClient(UserEntity client) {
        this.client = client;
    }

}

