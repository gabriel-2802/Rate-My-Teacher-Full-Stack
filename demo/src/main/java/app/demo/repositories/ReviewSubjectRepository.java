package app.demo.repositories;

import app.demo.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewSubjectRepository extends JpaRepository<Review, Long> {

}
