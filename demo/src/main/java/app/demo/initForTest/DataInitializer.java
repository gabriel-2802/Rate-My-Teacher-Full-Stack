package app.demo.initForTest;

import app.demo.entities.Role;
import app.demo.entities.User;
import app.demo.repositories.RoleRepository;
import app.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        // Ensure roles exist
        if (!roleRepository.existsByAuthority("ROLE_USER")) {
            roleRepository.save(new Role("ROLE_USER"));
        }
        if (!roleRepository.existsByAuthority("ROLE_ADMIN")) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }

        // Fetch fresh roles from database
        Role adminRole = roleRepository.findByAuthority("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found in DB"));
        Role userRole = roleRepository.findByAuthority("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found in DB"));

        // Initialize admin user
        if (userRepository.findByUsername("gabriel").isEmpty()) {
            User admin = new User();
            admin.setUsername("gabriel");
            admin.setPassword(passwordEncoder.encode("gabriel"));
            admin.setEmail("gabriel@example.com");
            admin.setRoles(new HashSet<>(Set.of(adminRole)));
            userRepository.save(admin);
        }

        // Initialize normal user
        if (userRepository.findByUsername("mario").isEmpty()) {
            User user = new User();
            user.setUsername("mario");
            user.setPassword(passwordEncoder.encode("mario"));
            user.setEmail("mario@example.com");
            user.setRoles(new HashSet<>(Set.of(userRole)));
            userRepository.save(user);
        }
    }
}