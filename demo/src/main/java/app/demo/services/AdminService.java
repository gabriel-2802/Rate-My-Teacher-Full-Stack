package app.demo.services;

import app.demo.dto.UserDTO;
import app.demo.entities.User;
import app.demo.mappers.UserMapper;
import app.demo.repositories.ReviewSubjectRepository;
import app.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final ReviewSubjectRepository reviewSubjectRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        var users = userRepository.findAll();
        return userMapper.toDTO(users);
    }

    public UserDTO getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return userMapper.toDTO(user.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
