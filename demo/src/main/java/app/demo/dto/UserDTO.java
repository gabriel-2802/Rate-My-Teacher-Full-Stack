package app.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private List<ReviewDTO> reviews;
    private Set<RoleDTO> roles;
    private byte[] profilePicture;
}
