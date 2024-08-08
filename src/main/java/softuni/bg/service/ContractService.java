package softuni.bg.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.bg.model.dtos.ContractDTO;
import softuni.bg.model.dtos.info.ContractInfoDTO;
import softuni.bg.model.entity.Application;
import softuni.bg.model.entity.Contract;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.model.enums.ContractStatus;
import softuni.bg.repository.ApplicationRepository;
import softuni.bg.repository.ContractRepository;
import softuni.bg.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public ContractService(ContractRepository contractRepository, ModelMapper modelMapper, ApplicationRepository applicationRepository, UserRepository userRepository) {
        this.contractRepository = contractRepository;
        this.modelMapper = modelMapper;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    public List<ContractDTO> findAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        return contracts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ContractInfoDTO> findById(Long id) {
        Optional<Contract> contractOptional = contractRepository.findById(id);
        return contractOptional.map(this::convertToInfoDTO);
    }
    public Contract getContractById(Long id){
        return contractRepository.findById(id).get();
    }

    public List<ContractInfoDTO> findByFreelancer(UserEntity freelancer) {
        List<Contract> contracts = contractRepository.findByFreelancer(freelancer);
        return contracts.stream().map(this::convertToInfoDTO).toList();
    }

    public List<ContractDTO> findByClient(UserEntity client) {
        List<Contract> contracts = contractRepository.findByClient(client);
        return contracts.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public void deleteContract(Long contractId) {
        Optional<Contract> currentContract = contractRepository.findById(contractId);
        if (currentContract.isEmpty()){
            throw new RuntimeException("Contract not found.");
        }
        List<Application> allByJobListingId = applicationRepository.findAllByJobListingId(currentContract.get().getApplication().getJobListing().getId());
        allByJobListingId.forEach(application -> application.setStatus("Pending"));
        applicationRepository.saveAll(allByJobListingId);
        contractRepository.deleteById(contractId);
    }

    private ContractDTO convertToDTO(Contract contract) {
        return modelMapper.map(contract, ContractDTO.class);
    }

    private ContractInfoDTO convertToInfoDTO(Contract contract) {
        return modelMapper.map(contract, ContractInfoDTO.class);
    }

    private Contract convertToEntity(ContractDTO contractDTO) {
        return modelMapper.map(contractDTO, Contract.class);
    }

    public void createContract(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).
                orElseThrow(() -> new IllegalArgumentException("Invalid application ID"));


        applicationRepository.save(application);
        Contract contract = new Contract();
        contract.setApplication(application);
        contract.setClient(application.getJobListing().getClient());
        contract.setFreelancer(application.getFreelancer());
        contract.setTerms("Default terms");  // TODO: i should make it dynamically
        contract.setStatus("Pending");

        //setting status rejected to other applications
        List<Application> byJobListingId = applicationRepository.findByJobListingId(application.getJobListing().getId());
        for (Application application1 : byJobListingId) {
            application1.setStatus("Rejected");
            applicationRepository.save(application1);
        }
        application.setStatus("Approved");
        applicationRepository.save(application);
        contractRepository.save(contract);
    }


    public UserEntity returnOtherUser(Contract contract,UserEntity loggedUser){
        if (contract.getClient().getId() == loggedUser.getId()){
            return userRepository.findById(contract.getFreelancer().getId()).get();
        }
        else return userRepository.findById(contract.getClient().getId()).get();
    }
}
