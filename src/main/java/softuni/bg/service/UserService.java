package softuni.bg.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.bg.model.FreelanceUserDetails;
import softuni.bg.model.dtos.*;
import softuni.bg.model.dtos.info.ContractInfoDTO;
import softuni.bg.model.entity.Contract;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.Role;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.model.enums.RoleName;
import softuni.bg.repository.RoleRepository;
import softuni.bg.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public List<ContractInfoDTO> getUserContracts(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Contract> contracts = new ArrayList<>();
        contracts.addAll(userEntity.getFreelancerContracts());
        contracts.addAll(userEntity.getClientContracts());
        return contracts.stream()
                .map(contract -> modelMapper.map(contract, ContractInfoDTO.class))
                .collect(Collectors.toList());
    }
    @Transactional
    public void registerUser(UserRegistrationDTO userRegistrationDTO,RoleName roleName) {
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByUsername(userRegistrationDTO.getUsername()) ||
                userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new IllegalArgumentException("Username or Email already taken");
        }
        UserEntity userEntity = modelMapper.map(userRegistrationDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        userEntity.getRoles().add(role);
        userRepository.save(userEntity);
    }


    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO convertToDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());

        // Save and convert to DTO
        UserEntity updatedUser = userRepository.save(userEntity);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(userId);
    }

    public UserDTO findUserByUsername(String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return modelMapper.map(userOptional.get(), UserDTO.class);
        } else {
                   throw new RuntimeException("User not found with username: " + username);
        }
    }

    public void save(UserDTO loggedUser) {
        UserEntity map = modelMapper.map(loggedUser, UserEntity.class);
        userRepository.save(map);
    }


//    public List<JobListingDTO> getUserJobListings(Long userId) {
//        UserEntity userEntity = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        List<JobListing> jobListings = userEntity.getJobListings();
//
//        return jobListings.stream()
//                .map(jobListing -> modelMapper.map(jobListing, JobListingDTO.class))
//                .collect(Collectors.toList());
//    }

//    public Optional<FreelanceUserDetails> getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null &&
//                authentication.getPrincipal() instanceof FreelanceUserDetails freelanceUserDetails) {
//            return Optional.of(freelanceUserDetails);
//        }
//        return Optional.empty();
//    }
}
