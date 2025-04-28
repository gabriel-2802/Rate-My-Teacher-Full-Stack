package app.demo.repositories;

import app.demo.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewSubjectRepository extends JpaRepository<Review, Long> {

}
