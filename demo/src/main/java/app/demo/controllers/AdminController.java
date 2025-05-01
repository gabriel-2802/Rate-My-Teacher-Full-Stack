package app.demo.controllers;

import app.demo.dto.UserDTO;
import app.demo.entities.User;
import app.demo.mappers.UserMapper;
import app.demo.repositories.ReviewSubjectRepository;
import app.demo.repositories.UserRepository;
import app.demo.services.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;


    @GetMapping("/users")
    @Transactional
    ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> users = adminService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/users/{username}")
    @Transactional
    ResponseEntity<UserDTO> getUser(@PathVariable String username) {
        try {
            UserDTO user = adminService.getUser(username);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e ) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
