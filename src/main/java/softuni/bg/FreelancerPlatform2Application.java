package softuni.bg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FreelancerPlatform2Application {

	public static void main(String[] args) {
		SpringApplication.run(FreelancerPlatform2Application.class, args);
	}

}
