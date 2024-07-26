package softuni.bg.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.bg.model.dtos.JobListingDTO;
import softuni.bg.model.dtos.UserDTO;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.UserEntity;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}
