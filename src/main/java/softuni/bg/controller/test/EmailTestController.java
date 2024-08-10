package softuni.bg.controller.test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softuni.bg.service.impl.EmailService;

@RestController
public class EmailTestController {

    private final EmailService emailService;

    @Autowired
    public EmailTestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-test-email")
    public String sendTestEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        emailService.sendEmail(to, subject, body);
        return "Email sent successfully!";
    }
}
