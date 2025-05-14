package app.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class    UniversityDTO {
    private Long id;
    private String name;
    private Double rating;
    private String city;
    private byte[] profilePicture;
    private List<TeacherDTO> teachers;
}
