package softuni.bg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuni.bg.model.dtos.ContractDTO;
import softuni.bg.model.dtos.UserDTO;
import softuni.bg.model.dtos.info.ContractInfoDTO;
import softuni.bg.service.ContractService;
import softuni.bg.service.UserService;

import java.security.Principal;
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
        ContractInfoDTO contractDTO = contractService.findById(contractId)
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
        ContractInfoDTO ContractInfoDTO = contractService.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        model.addAttribute("contractDTO", ContractInfoDTO);
        return "edit-contract";
    }

    @PostMapping("/delete/{contractId}")
    public String deleteContract(@PathVariable Long contractId) {
        contractService.deleteContract(contractId);
        return "redirect:/contracts/list";
    }

    @Transactional
    @GetMapping
    public String showUserContracts(Principal principal, Model model) {
        UserDTO userByUsername = userService.findUserByUsername(principal.getName());
        Long id = userByUsername.getId();
        List<ContractInfoDTO> myContracts = userService.getUserContracts(id);

        model.addAttribute("myContracts", myContracts);
        return "contracts";
    }
    @PostMapping("/create")
    public String createContract(@RequestParam("applicationId") Long applicationId) {
        // Create contract logic
        contractService.createContract(applicationId);

        // Redirect to the job applications page or any other appropriate page
        return "redirect:/applications/job/" + applicationId;
    }

}
//        UserEntity currentUser = userService.findById(id)
//                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
//        List<Contract> myContracts = new ArrayList<>();
//        List<Contract> clientContracts = currentUser.getClientContracts();
//        List<Contract> freelancerContracts = currentUser.getFreelancerContracts();
//        if (clientContracts.isEmpty()){
//            myContracts.addAll(freelancerContracts);
//        }else {
//            myContracts.addAll(clientContracts);
//        }