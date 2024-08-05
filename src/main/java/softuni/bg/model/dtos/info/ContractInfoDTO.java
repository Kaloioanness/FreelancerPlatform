package softuni.bg.model.dtos.info;

import softuni.bg.model.entity.Application;
import softuni.bg.model.entity.UserEntity;

public class ContractInfoDTO {
    private Long id;
    private String terms;
    private String status;
    private UserEntity client;
    private UserEntity freelancer;
    private Application application;

    //getters and setters

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

    public UserEntity getClient() {
        return client;
    }

    public void setClient(UserEntity client) {
        this.client = client;
    }

    public UserEntity getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(UserEntity freelancer) {
        this.freelancer = freelancer;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
