package softuni.bg.service.impl;
import org.springframework.stereotype.Service;
@Service
public class NotificationService {

    private final EmailService emailService;


    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendNotification(String email, String message) {
        emailService.sendEmail(email, "Weekly Reminder", message);
    }
}