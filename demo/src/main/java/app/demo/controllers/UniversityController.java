package app.demo.controllers;

import app.demo.dto.ReviewDTO;
import app.demo.dto.UniversityDTO;
import app.demo.exceptions.UniversityNotFoundException;
import app.demo.services.UniversityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {
    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    // Public for every user
    @GetMapping
    public List<UniversityDTO> list(@RequestParam(value = "name", required = false) String name) {
        if (name == null) {
            return universityService.getAllUniversities();
        }

        return universityService
                .getAllUniversities()
                .stream()
                .filter((univ) -> univ.getName().equals(name))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityDTO> get(@PathVariable Long id) {
        try {
            UniversityDTO university = universityService.getUniversity(id);
            return ResponseEntity.ok(university);
        } catch (UniversityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Authenticated users
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/{id}/reviews")
    @Transactional
    public ResponseEntity<ReviewDTO> addReview(
            @PathVariable Long id,
            @RequestBody @Valid ReviewDTO reviewDTO,
            @AuthenticationPrincipal UserDetails user) {
        try {
            ReviewDTO review = universityService.addReview(id, reviewDTO, user.getUsername());
            URI location = URI.create("/api/universities/" + id + "/reviews/" + review.getId());
            return ResponseEntity.created(location).body(review);
        } catch (UniversityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UniversityDTO create(@RequestBody UniversityDTO universityDTO) {
        return universityService.createUniversity(universityDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UniversityDTO> update(@PathVariable Long id, @RequestBody UniversityDTO universityDTO) {
        try {
            UniversityDTO university = universityService.updateUniversity(id, universityDTO);
            return ResponseEntity.ok(university);
        } catch (UniversityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            universityService.deleteUniversity(id);
            return ResponseEntity.ok().build();
        } catch (UniversityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
