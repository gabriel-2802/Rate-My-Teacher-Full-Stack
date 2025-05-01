package app.demo.services;

import app.demo.dto.AuthDTO;
import app.demo.dto.LoginDTO;
import app.demo.dto.RegisterDTO;
import app.demo.entities.Role;
import app.demo.entities.User;
import app.demo.exceptions.ExistingEmailException;
import app.demo.exceptions.ExistingUsernameException;
import app.demo.exceptions.ResourceNotFoundException;
import app.demo.repositories.RoleRepository;
import app.demo.repositories.UserRepository;
import app.demo.security.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    public void register(RegisterDTO registerDTO) throws ExistingUsernameException, ExistingEmailException, ResourceNotFoundException {

        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new ExistingUsernameException("Username '" + registerDTO.getUsername() + "' already exists");
        }

        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new ExistingEmailException("Email '" + registerDTO.getEmail() + "' already exists");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role role = roleRepository.findByAuthority("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));

        user.setRoles(Set.of(role));
        userRepository.save(user);
    }


    public AuthDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtGenerator.generateToken(authentication);
        return new AuthDTO(jwt);
    }

}
