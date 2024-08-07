package softuni.bg.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import softuni.bg.config.ThymeleafUtility;
import softuni.bg.model.dtos.UserDTO;
import softuni.bg.model.entity.UserEntity;
import softuni.bg.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final ThymeleafUtility thymeleafUtility;

    public ProfileController(UserService userService, ThymeleafUtility thymeleafUtility) {
        this.userService = userService;
        this.thymeleafUtility = thymeleafUtility;
    }

    @GetMapping
    public String showProfilePage(Principal principal, Model model) {

        UserDTO loggedUser = userService.findUserByUsername(principal.getName());
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("thymeleafUtility", thymeleafUtility);
        return "profile";
    }
    @PostMapping("/updatePicture")
    public String updateProfilePicture(@RequestParam("imageUrl") String imageUrl, Principal principal) {
        UserDTO loggedUser = userService.findUserByUsername(principal.getName());
        loggedUser.setImageUrl(imageUrl);
        userService.save(loggedUser);
        return "redirect:/profile";
    }
}
