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
public class AdminReviewController {
    final ReviewSubjectService reviewSubjectService;

    public AdminReviewController(ReviewSubjectService reviewSubjectService) {
        this.reviewSubjectService = reviewSubjectService;
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
