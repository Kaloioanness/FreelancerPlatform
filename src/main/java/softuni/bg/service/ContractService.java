package softuni.bg.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.bg.model.dtos.ContractDTO;
import softuni.bg.model.entity.Application;
import softuni.bg.model.entity.Contract;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.model.enums.ContractStatus;
import softuni.bg.repository.ApplicationRepository;
import softuni.bg.repository.ContractRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;
    private final ApplicationRepository applicationRepository;

    public ContractService(ContractRepository contractRepository, ModelMapper modelMapper, ApplicationRepository applicationRepository) {
        this.contractRepository = contractRepository;
        this.modelMapper = modelMapper;
        this.applicationRepository = applicationRepository;
    }

    public List<ContractDTO> findAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        return contracts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ContractDTO> findById(Long id) {
        Optional<Contract> contractOptional = contractRepository.findById(id);
        return contractOptional.map(this::convertToDTO);
    }

    public List<ContractDTO> findByFreelancer(UserEntity freelancer) {
        List<Contract> contracts = contractRepository.findByFreelancer(freelancer);
        return contracts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ContractDTO> findByClient(UserEntity client) {
        List<Contract> contracts = contractRepository.findByClient(client);
        return contracts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteContract(Long contractId) {
        contractRepository.deleteById(contractId);
    }

    private ContractDTO convertToDTO(Contract contract) {
        return modelMapper.map(contract, ContractDTO.class);
    }

    private Contract convertToEntity(ContractDTO contractDTO) {
        return modelMapper.map(contractDTO, Contract.class);
    }

    public void createContract(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).
                orElseThrow(() -> new IllegalArgumentException("Invalid application ID"));

        Contract contract = new Contract();
        contract.setApplication(application);
        contract.setClient(application.getJobListing().getClient());
        contract.setFreelancer(application.getFreelancer());
        contract.setTerms("Default terms");  // You can set this dynamically as needed
        contract.setStatus("Pending");       // Initial status

        contractRepository.save(contract);
    }
}
