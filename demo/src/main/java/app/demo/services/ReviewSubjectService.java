package app.demo.services;

import app.demo.dto.ReviewDTO;
import app.demo.entities.Review;
import app.demo.entities.ReviewSubject;
import app.demo.exceptions.ReviewNotFoundException;
import app.demo.mappers.ReviewMapper;
import app.demo.repositories.ReviewSubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewSubjectService {
    private final ReviewSubjectRepository reviewSubjectRepository;
    private final ReviewMapper reviewMapper;

    public ReviewSubjectService(ReviewSubjectRepository reviewSubjectRepository,
                                ReviewMapper reviewMapper) {
        this.reviewSubjectRepository = reviewSubjectRepository;
        this.reviewMapper = reviewMapper;
    }

    public List<ReviewDTO> getAllReviews() {
        List<Review> allReviews = reviewSubjectRepository.findAll();
        return reviewMapper.toDTO(allReviews);
    }

    public ReviewDTO approveReview(Long reviewId) throws ReviewNotFoundException {
        Review review = reviewSubjectRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("review not found"));
        review.setApproved(true);

        ReviewSubject subject = review.getReviewSubject();
        subject.setRating(subject.calculateRating());

        return reviewMapper.toDTO(reviewSubjectRepository.save(review));
    }
}
