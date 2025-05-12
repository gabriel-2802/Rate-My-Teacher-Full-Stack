package app.demo.mappers;

import app.demo.dto.TeacherDTO;
import app.demo.entities.Teacher;
import app.demo.entities.University;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class})
public interface TeacherMapper {
    @Mapping(source = "university.id", target = "universityId")
    TeacherDTO toDTO(Teacher teacher);

    List<TeacherDTO> toDTOList(List<Teacher> teachers);

    @Mapping(target = "university", expression = "java(mapUniversityIdToUniversity(teacherDTO.getUniversityId()))")
    Teacher toEntity(TeacherDTO teacherDTO);

    default University mapUniversityIdToUniversity(Long id) {
        if (id == null) return null;
        University u = new University();
        u.setId(id);
        return u;
    }
}