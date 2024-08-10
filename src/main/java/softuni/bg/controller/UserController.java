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
    public String register(@Valid @ModelAttribute("registerDTO") UserRegistrationDTO registerDTO, BindingResult bindingResult, RedirectAttributes rda) {
        if (bindingResult.hasErrors() || !registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            rda.addFlashAttribute("registerDTO", registerDTO);
            rda.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);
            return "/register"; //
        }

        RoleName roleName = RoleName.valueOf(registerDTO.getRole().toString());
        userService.registerUser(registerDTO, roleName);
        return "redirect:/login";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users-list"; // Return the view that lists all users
    }

    @GetMapping("/{userId}")
    public String getUserById(@PathVariable Long userId, Model model) {
        UserDTO userDTO = userService.findById(userId)
                .map(userService::convertToDTO)
                .orElse(null);

        if (userDTO != null) {
            model.addAttribute("user", userDTO);
            return "user-details";
        } else {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }
    @PostMapping("/{userId}/update")
    public String updateUser(@PathVariable Long userId,
                             @ModelAttribute("user") @Valid UserDTO userDTO,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.user", result);
            return "user-details";
        }

        try {
            UserDTO updatedUser = userService.updateUser(userId, userDTO);
            model.addAttribute("user", updatedUser);
            return "user-details";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred while updating the user.");
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        try {
            userService.deleteUser(id);
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
