package softuni.bg.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("javax.servlet.error.status_code");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 404) {
                model.addAttribute("error", "The page you are looking for does not exist.");
                return "error";
            } else if (statusCode == 500) {
                model.addAttribute("error", "Internal server error. Please try again later.");
                return "error";
            }
        }

        model.addAttribute("error", "An unexpected error occurred.");
        return "error";
    }

}
