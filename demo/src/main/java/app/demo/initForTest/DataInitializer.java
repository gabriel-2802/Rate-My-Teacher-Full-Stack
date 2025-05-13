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

        // Initialize normal user
//        Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYXJpb1VzZXIiLCJyb2xlcyI6IlJPTEVfVVNFUiIsImlhdCI6MTc0NzE1MjkzOSwiZXhwIjoxNzQ3NzU3NzM5fQ.bDPARtUePGX2c-Xb-fjUP-JX2WrQGbd9ca5FZ1RT0DQnjwhWOxLgn9VX6h43mPMuK_aXlPUnRGtxI65KZCAmsQ
        if (userRepository.findByUsername("MarioUser").isEmpty()) {
            User admin = new User();
            admin.setUsername("MarioUser");
            admin.setPassword(passwordEncoder.encode("MarioUser"));
            admin.setEmail("mario@user.ro");
            admin.setRoles(new HashSet<>(Set.of(userRole)));
            userRepository.save(admin);
        }

//    Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYXJpb0Jvc3MiLCJyb2xlcyI6IlJPTEVfVVNFUiIsImlhdCI6MTc0NzE0NTM1MCwiZXhwIjoxNzQ3NzUwMTUwfQ.hJ7bwTmvU9pbTgiSFNrlBgi_pNAu-7fE9HhMmT13DKD2F3AFTJuTJIVj_LPsOvIIi_h6aiHAWVPMCZOzngYHdg
        if (userRepository.findByUsername("MarioBoss").isEmpty()) {
            User user = new User();
            user.setUsername("MarioBoss");
            user.setPassword(passwordEncoder.encode("MarioBoss"));
            user.setEmail("mario@email.ro");
            user.setRoles(new HashSet<>(Set.of(adminRole)));
            userRepository.save(user);
        }
    }
}