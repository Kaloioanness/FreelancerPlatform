//package softuni.bg.service;
//
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import softuni.bg.model.dtos.UserRegistrationDTO;
//import softuni.bg.model.entity.UserEntity;
//import softuni.bg.repository.UserRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Test
//    public void testRegisterUser() {
//        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
//        registrationDTO.setUsername("testuser");
//        registrationDTO.setFirstName("Test");
//        registrationDTO.setLastName("User");
//        registrationDTO.setEmail("testuser@example.com");
//        registrationDTO.setPassword("password");
//        registrationDTO.setConfirmPassword("password");
//
//        userService.registerUser(registrationDTO);
//
//        UserEntity userEntity = userRepository.findByUsername("testuser").orElse(null);
//        assertThat(userEntity).isNotNull();
//        assertThat(userEntity.getUsername()).isEqualTo("testuser");
//        assertThat(passwordEncoder.matches("password", userEntity.getPassword())).isTrue();
//    }
//}
//
