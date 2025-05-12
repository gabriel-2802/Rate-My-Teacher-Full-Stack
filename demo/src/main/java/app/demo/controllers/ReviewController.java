package app.demo.controllers;

import app.demo.dto.ReviewDTO;
import app.demo.exceptions.ReviewNotFoundException;
import app.demo.services.ReviewSubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reviews")
public class ReviewController {
    final ReviewSubjectService reviewSubjectService;

    public ReviewController(ReviewSubjectService reviewSubjectService) {
        this.reviewSubjectService = reviewSubjectService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public ResponseEntity<List<ReviewDTO>> listForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewSubjectService.getAllReviewsForUser(userId));
    }

    @GetMapping("/{universityId}")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public ResponseEntity<List<ReviewDTO>> listForUniversity(@PathVariable Long universityId) {
        return ResponseEntity.ok(reviewSubjectService.getAllReviewsForUniversity(universityId));
    }

    @GetMapping("/{teacherId}")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public ResponseEntity<List<ReviewDTO>> listForTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(reviewSubjectService.getAllReviewsForTeacher(teacherId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<List<ReviewDTO>> list() {
        return ResponseEntity.ok(reviewSubjectService.getAllReviews());
    }

    @PutMapping("/{reviewId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<ReviewDTO> approve(@PathVariable Long reviewId) {
        ReviewDTO approved = null;
        try {
            approved = reviewSubjectService.approveReview(reviewId);
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(approved);
    }
}
