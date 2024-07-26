package softuni.bg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.bg.model.entity.JobListing;
import softuni.bg.model.entity.Review;
import softuni.bg.model.entity.UserEntity;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByReviewer(UserEntity reviewer);

    List<Review> findByReviewee(UserEntity reviewee);

}
