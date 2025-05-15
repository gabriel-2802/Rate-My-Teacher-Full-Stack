package app.demo.controllers;

import app.demo.dto.ReviewDTO;
import app.demo.exceptions.ReviewNotFoundException;
import app.demo.services.ReviewSubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    final ReviewSubjectService reviewSubjectService;

    public ReviewController(ReviewSubjectService reviewSubjectService) {
        this.reviewSubjectService = reviewSubjectService;
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<List<ReviewDTO>> listForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewSubjectService.getAllReviewsForUser(userId));
    }

    @GetMapping("/university/{universityId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<List<ReviewDTO>> listForUniversity(@PathVariable Long universityId) {
        return ResponseEntity.ok(reviewSubjectService.getAllReviewsForUniversity(universityId));
    }

    @GetMapping("/teacher/{teacherId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<List<ReviewDTO>> getPendingReviews() {
        return ResponseEntity.ok(reviewSubjectService.getPendingReviews());
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

    @DeleteMapping("/{reviewId}/refuse")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Void> refuse(@PathVariable Long reviewId) {
        try {
            ReviewDTO refused = reviewSubjectService.refuseReview(reviewId);
            return ResponseEntity.ok().build();
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            reviewSubjectService.deleteReviewIfAuthorized(reviewId, userDetails.getUsername());
            return ResponseEntity.ok().build();
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
