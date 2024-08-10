package softuni.bg.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import softuni.bg.model.dtos.info.UserSchedulingDTO;
import softuni.bg.service.UserService;

import java.util.List;

@Service
public class ScheduledTasks {

    private final UserService userService;
    private final NotificationService notificationService;

    @Autowired
    public ScheduledTasks(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 08 13 * * ?")
    public void sendWeeklyReminders() {
        List<UserSchedulingDTO> users = userService.findAllUsersForScheduling();

        for (UserSchedulingDTO user : users) {
            String message = "Reminder: Check out the latest job listings, ratings and updates on our website 'FreelancerPlatform V2.0'!";
            notificationService.sendNotification(user.getEmail(), message);
        }

        System.out.println("Weekly reminders sent to all users.");
    }
}