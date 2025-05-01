package app.demo;

import app.demo.entities.Role;
import app.demo.repositories.RoleRepository;
import app.demo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RateMyTeacherApplication {
	public static void main(String[] args) {
		SpringApplication.run(RateMyTeacherApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (roleRepository.existsByAuthority("ROLE_USER")) {
				return;
			}

			roleRepository.save(new Role("ROLE_USER"));
			roleRepository.save(new Role("ROLE_ADMIN"));

		};
	}
}
