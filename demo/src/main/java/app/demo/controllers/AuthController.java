package app.demo.controllers;

import app.demo.dto.AuthDTO;
import app.demo.dto.LoginDTO;
import app.demo.dto.RegisterDTO;
import app.demo.exceptions.ExistingEmailException;
import app.demo.exceptions.ExistingUsernameException;
import app.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        try {
            authService.register(registerDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (ExistingUsernameException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (ExistingEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during registration : " +  e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            AuthDTO response = authService.login(loginDTO);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new AuthDTO("Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new AuthDTO("An error occurred: " + e.getMessage()));
        }
    }

}
