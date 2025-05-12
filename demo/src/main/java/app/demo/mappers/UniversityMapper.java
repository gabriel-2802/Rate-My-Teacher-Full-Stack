package app.demo.mappers;

import app.demo.dto.UniversityDTO;
import app.demo.entities.University;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class, TeacherMapper.class})
public interface UniversityMapper {

    UniversityDTO toDTO(University university);

    List<UniversityDTO> toDTOList(List<University> universities);

    University toEntity(UniversityDTO universityDTO);
}