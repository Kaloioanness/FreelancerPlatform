package softuni.bg.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import softuni.bg.model.dtos.ContractDTO;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.model.enums.ContractStatus;
import softuni.bg.service.ContractService;
import softuni.bg.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/contracts")
public class ContractController {
    private final ContractService contractService;
    private final UserService userService;
    public ContractController(ContractService contractService, UserService userService) {
        this.contractService = contractService;
        this.userService = userService;
    }

    @GetMapping("/{contractId}")
    public String viewContractDetails(@PathVariable Long contractId, Model model) {
        ContractDTO contractDTO = contractService.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        model.addAttribute("contract", contractDTO);
        return "contract-details";
    }

    @GetMapping("/list")
    public String viewAllContracts(Model model) {
        List<ContractDTO> contracts = contractService.findAllContracts();
        model.addAttribute("contracts", contracts);
        return "contracts";
    }



    @GetMapping("/edit/{contractId}")
    public String showEditContractForm(@PathVariable Long contractId, Model model) {
        ContractDTO contractDTO = contractService.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        model.addAttribute("contractDTO", contractDTO);
        return "edit-contract";
    }

    @PostMapping("/delete/{contractId}")
    public String deleteContract(@PathVariable Long contractId) {
        contractService.deleteContract(contractId);
        return "redirect:/contracts/list";
    }

    @GetMapping
    public String findByFreelancer(@RequestParam Long freelancerId, Model model) {
        UserEntity freelancer = userService.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("User with id " + freelancerId + " not found"));
        List<ContractDTO> myContract = contractService.findByFreelancer(freelancer);
        model.addAttribute("myContract", myContract);
        return "contracts";
    }

    @GetMapping("/client")
    public String findByClient(@RequestParam Long clientId, Model model) {
        UserEntity client = userService.findById(clientId)
                .orElseThrow(() -> new RuntimeException("User with id " + clientId + " not found"));
        List<ContractDTO> contracts = contractService.findByClient(client);
        model.addAttribute("contracts", contracts);
        return "contracts";
    }


    @PostMapping("/create")
    public String createContract(@RequestParam("applicationId") Long applicationId, Model model) {
        // Create contract logic
        contractService.createContract(applicationId);

        // Redirect to the job applications page or any other appropriate page
        return "redirect:/client/job-applications";
    }

}
