package softuni.bg.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle 404 errors
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError(NoHandlerFoundException ex, Model model) {
        model.addAttribute("error", "The page you are looking for does not exist.");
        return "error";
    }

    // Handle other exceptions
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("error", "An unexpected error occurred: " + ex.getMessage());
        return "error";
    }
}
