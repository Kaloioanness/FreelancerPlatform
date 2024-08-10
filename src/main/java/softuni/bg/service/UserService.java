package softuni.bg.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.bg.model.FreelanceUserDetails;
import softuni.bg.model.dtos.*;
import softuni.bg.model.dtos.info.ContractInfoDTO;
import softuni.bg.model.dtos.info.UserSchedulingDTO;
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
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));


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
                .map(this::convertToDTO).toList();
    }

    public UserDTO convertToDTO(UserEntity userEntity) {
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);

        // Manually mapping the roles
        userDTO.setRoles(userEntity.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));

        return userDTO;
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
            return convertToDTO(userOptional.get());
//            return modelMapper.map(userOptional.get(), UserDTO.class);
        } else {
                   throw new RuntimeException("User not found with username: " + username);
        }
    }

    public void save(UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userDTO.getId()));

        // Update basic fields
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setImageUrl(userDTO.getImageUrl());

        // Load and set roles from database to avoid transient issues, because I had a lot.
        List<Role> roles = userDTO.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toList());

        userEntity.setRoles(roles);

        userRepository.save(userEntity);
    }




    //SCHEDULING... NEEDED LOGIC
    public List<UserSchedulingDTO> findAllUsersForScheduling() {
        return userRepository.findAll().stream()
                .map(this::convertToDTOForScheduling)
                .toList();
    }

    private UserSchedulingDTO convertToDTOForScheduling(UserEntity user) {
        return new UserSchedulingDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}
