package app.demo.mappers;

import app.demo.dto.UserDTO;
import app.demo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class, RoleMapper.class})
public interface UserMapper {

    UserDTO toDTO(User user);

    List<UserDTO> toDTO(List<User> users);
}
