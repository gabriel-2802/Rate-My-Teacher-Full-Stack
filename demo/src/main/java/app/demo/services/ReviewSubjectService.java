package app.demo.services;

import app.demo.dto.ReviewDTO;
import app.demo.dto.UserDTO;
import app.demo.entities.Review;
import app.demo.entities.ReviewSubject;
import app.demo.entities.User;
import app.demo.exceptions.ReviewNotFoundException;
import app.demo.mappers.ReviewMapper;
import app.demo.repositories.ReviewSubjectRepository;
import app.demo.repositories.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewSubjectService {
    private final ReviewSubjectRepository reviewSubjectRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;

    public ReviewSubjectService(ReviewSubjectRepository reviewSubjectRepository,
                                ReviewMapper reviewMapper,
                                UserRepository userRepository) {
        this.reviewSubjectRepository = reviewSubjectRepository;
        this.reviewMapper = reviewMapper;
        this.userRepository = userRepository;
    }

    public List<ReviewDTO> getAllReviews() {
        List<Review> allReviews = reviewSubjectRepository.findAll();
        return reviewMapper.toDTO(allReviews);
    }

    public List<ReviewDTO> getPendingReviews() {
        List<Review> pendingReviews = reviewSubjectRepository.findAll()
                .stream()
                .filter(review -> !review.isApproved())
                .toList();

        return reviewMapper.toDTO(pendingReviews);
    }

    public List<ReviewDTO> getAllReviewsForUser(Long userId) {
        List<Review> allReviews = reviewSubjectRepository.findAll()
                .stream()
                .filter(review -> review.getAuthor().getId().equals(userId))
                .filter(Review::isApproved)
                .toList();

        return reviewMapper.toDTO(allReviews);
    }

    public List<ReviewDTO> getAllReviewsForUniversity(Long universityId) {
        List<Review> allReviews = reviewSubjectRepository.findAll()
                .stream()
                .filter(review -> review.getReviewSubject().getId().equals(universityId))
                .filter(Review::isApproved)
                .toList();

        return reviewMapper.toDTO(allReviews);
    }

    public List<ReviewDTO> getAllReviewsForTeacher(Long teacherId) {
        List<Review> allReviews = reviewSubjectRepository.findAll()
                .stream()
                .filter(review -> review.getReviewSubject().getId().equals(teacherId))
                .filter(Review::isApproved)
                .toList();

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

    public ReviewDTO refuseReview(Long reviewId) throws ReviewNotFoundException {
        Review review = reviewSubjectRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("review not found"));

        reviewSubjectRepository.delete(review);

        try {
            ReviewSubject subject = review.getReviewSubject();
            subject.getReviews().remove(review);
        } catch (Exception ignored) {

        }

        return reviewMapper.toDTO(review);
    }

    @Transactional
    public void deleteReviewIfAuthorized(Long reviewId, String username)
            throws ReviewNotFoundException, AccessDeniedException {
        Review review = reviewSubjectRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with ID: " + reviewId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));


        if (!review.isApproved() && user.getRoles().stream().noneMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("User is not authorized to delete this review");
        }

        if (review.getAuthor().getId().equals(user.getId()) ||
                user.getRoles().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {

            ReviewSubject subject = review.getReviewSubject();
            subject.getReviews().remove(review);

            reviewSubjectRepository.delete(review);

            if (subject.getReviews().stream().anyMatch(Review::isApproved)) {
                subject.setRating(subject.calculateRating());
            } else {
                if (subject.getReviews().isEmpty()) {
                    subject.setRating(0.0);
                } else {
                    subject.setRating(subject.calculateRating());
                }
            }
        } else {
            throw new AccessDeniedException("User is not authorized to delete this review");
        }
    }
}
