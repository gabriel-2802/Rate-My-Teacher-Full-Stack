package app.demo.controllers;

import app.demo.dto.ReviewDTO;
import app.demo.dto.TeacherDTO;
import app.demo.exceptions.TeacherNotFoundException;
import app.demo.exceptions.UserNotFoundException;
import app.demo.services.TeacherService;
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
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // Public for everyone
    @GetMapping
    public List<TeacherDTO> list(@RequestParam(value = "name", required = false) String name) {
        if (name == null) {
            return teacherService.getAllTeachers();
        }

        return teacherService
                .getAllTeachers()
                .stream()
                .filter((teacher) -> teacher.getName().equals(name))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> get(@PathVariable Long id) {
        try {
            TeacherDTO teacher = teacherService.getTeacher(id);
            return ResponseEntity.ok(teacher);
        } catch (TeacherNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Authenticated users
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/reviews")
    @Transactional
    public ResponseEntity<ReviewDTO> addReview(
            @PathVariable Long id,
            @RequestBody @Valid ReviewDTO reviewDTO,
            @AuthenticationPrincipal UserDetails user
    ) {
        ReviewDTO rev = null;
        try {
            rev = teacherService.addReview(id, reviewDTO, user.getUsername());
        } catch (TeacherNotFoundException | UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        URI location = URI.create("/api/teachers/" + id + "/reviews/" + rev.getId());
        return ResponseEntity.created(location).body(rev);
    }

    // Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDTO create(@RequestBody TeacherDTO dto) {
        return teacherService.createTeacher(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> update(@PathVariable Long id, @RequestBody TeacherDTO dto) {
        try {
            var t = teacherService.updateTeacher(id, dto);
            return ResponseEntity.ok(t);
        } catch (TeacherNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok().build();
        } catch (TeacherNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
