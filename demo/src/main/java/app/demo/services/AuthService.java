package app.demo.services;

import app.demo.dto.AuthDTO;
import app.demo.dto.LoginDTO;
import app.demo.dto.RegisterDTO;
import app.demo.entities.Role;
import app.demo.entities.User;
import app.demo.repositories.RoleRepository;
import app.demo.repositories.UserRepository;
import app.demo.security.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    public ResponseEntity<String> register(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Optional<Role> role = roleRepository.findByAuthority("ROLE_USER");
        if (role.isPresent()) {
            user.setRoles(Set.of(role.get()));
        } else {
            return ResponseEntity.badRequest().body("Role not found");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<AuthDTO> login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtGenerator.generateToken(authentication);
            return ResponseEntity.ok(new AuthDTO(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new AuthDTO("Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthDTO("An error occurred: " + e.getMessage()));
        }
    }
}
