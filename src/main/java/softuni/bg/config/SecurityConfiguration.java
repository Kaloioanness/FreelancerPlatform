package softuni.bg.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import softuni.bg.repository.UserRepository;
import softuni.bg.service.impl.UserDetailsServiceImpl;

@Configuration
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
               .authorizeHttpRequests(
                       // Setup which URLs are available to who
                       authorizeRequests ->
                               authorizeRequests
                                       // All static resources to "common locations" (css, images, js)
                                       .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                       // some more resources for all users
                                       .requestMatchers("/job-listings").hasRole("FREELANCER")
                                       .requestMatchers("/applications/freelancer").hasRole("FREELANCER")
                                       .requestMatchers("/","/users/login","/users/register","/home", "/error").permitAll()
                                       .requestMatchers("/client-job-listings").hasRole("CLIENT")
                                       // all other resources should be authenticated
                                       .anyRequest()
                                       .authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                // Where is our custom login form?
                                .loginPage("/users/login")
                                // what is the name of the username parameter in the Login POST request?
                                .usernameParameter("username")
                                // what is the name of the password parameter in the Login POST request?
                                .passwordParameter("password")
                                // What will happen if the login is successful
                                .defaultSuccessUrl("/", true)
                                // What will happen if the login fails
                                .failureForwardUrl("/users/login-error")
                )
                .logout(
                        logout ->
                                logout
                                        // What is the logout URL
                                        .logoutUrl("/users/logout")
                                        // Where to go after successful logout
                                        .logoutSuccessUrl("/")
                                        // Invalidate the session after logout
                                        .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService(UserRepository userRepository){
        return new UserDetailsServiceImpl(userRepository);
    }
}
