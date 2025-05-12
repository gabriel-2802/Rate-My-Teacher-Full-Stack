package app.demo.mappers;

import app.demo.dto.ReviewDTO;
import app.demo.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "author.username", target = "authorUsername")
    @Mapping(source = "reviewSubject.id", target = "subjectId")
    ReviewDTO toDTO(Review review);

    List<ReviewDTO> toDTO(List<Review> reviews);


    Review toEntity(ReviewDTO reviewDTO);
}
