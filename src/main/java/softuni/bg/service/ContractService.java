package softuni.bg.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.bg.model.dtos.ContractDTO;
import softuni.bg.model.entity.Contract;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.model.enums.ContractStatus;
import softuni.bg.repository.ContractRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;

    public ContractService(ContractRepository contractRepository, ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.modelMapper = modelMapper;
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
}
