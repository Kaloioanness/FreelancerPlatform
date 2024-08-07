package softuni.bg.config;

import org.springframework.stereotype.Component;

@Component
public class ThymeleafUtility {

    public String getStarRating(double rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars.append("<i class='fas fa-star'></i>");
            } else {
                stars.append("<i class='far fa-star'></i>");
            }
        }
        return stars.toString();
    }
}