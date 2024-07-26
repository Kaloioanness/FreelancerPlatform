package softuni.bg.model.dtos;

import jakarta.persistence.Column;
import softuni.bg.model.entity.Application;
import softuni.bg.model.enums.ContractStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class ContractDTO {

    private Long id;
    private String terms;
    private String status;
    private Long clientId; // ID of the client
    private Long freelancerId; // ID of the freelancer
    private Long applicationId; // ID of the application

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }
}