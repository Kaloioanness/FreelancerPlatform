package softuni.bg.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.bg.model.dtos.UserDTO;
import softuni.bg.model.dtos.UserRegistrationDTO;
import softuni.bg.model.enums.RoleName;
import softuni.bg.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerDTO")
    public UserRegistrationDTO registerDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerDTO", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDTO") UserRegistrationDTO registerDTO, BindingResult bindingResult, RedirectAttributes rda, Model model) {
        if (bindingResult.hasErrors() || !registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            rda.addFlashAttribute("registerDTO", registerDTO);
            rda.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);
            return "/register"; //
        }

        RoleName roleName = RoleName.valueOf(registerDTO.getRole().toString());
        userService.registerUser(registerDTO, roleName);
        return "redirect:/login";
    }

    @GetMapping("/{userId}")
    public String getUserById(@PathVariable Long userId, Model model) {
        Optional<UserDTO> userDTO = userService.findById(userId)
                .map(userEntity -> userService.convertToDTO(userEntity));

        if (userDTO.isPresent()) {
            model.addAttribute("user", userDTO.get());
            return "user-details"; // Return user details view
        } else {
            model.addAttribute("error", "User not found");
            return "error"; // Return error view
        }
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users-list"; // Return the view that lists all users
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable Long userId, @ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user-details";
        }
        try {
            UserDTO updatedUser = userService.updateUser(userId, userDTO);
            model.addAttribute("user", updatedUser);
            return "user-details";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId, Model model) {
        try {
            userService.deleteUser(userId);
            return "redirect:/users"; // Redirect to the users list after deletion
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Return error view
        }
    }
}
