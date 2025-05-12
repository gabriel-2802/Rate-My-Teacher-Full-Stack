package app.demo.services;

import app.demo.dto.ReviewDTO;
import app.demo.dto.UniversityDTO;
import app.demo.entities.Review;
import app.demo.entities.University;
import app.demo.exceptions.UniversityNotFoundException;
import app.demo.mappers.ReviewMapper;
import app.demo.mappers.UniversityMapper;
import app.demo.repositories.UniversityRepository;
import app.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;
    private final UniversityMapper universityMapper;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    public UniversityService(UniversityRepository universityRepository,
                             UniversityMapper universityMapper,
                             UserRepository userRepository,
                             ReviewMapper reviewMapper) {
        this.universityRepository = universityRepository;
        this.universityMapper = universityMapper;
        this.userRepository = userRepository;
        this.reviewMapper = reviewMapper;
    }

    public List<UniversityDTO> getAllUniversities() {
        return universityMapper.toDTOList(universityRepository.findAllWithTeachers());
    }

    public UniversityDTO getUniversity(Long id) throws UniversityNotFoundException {
        return universityMapper.toDTO(universityRepository.findByIdWithTeachers(id)
                .orElseThrow(() -> new UniversityNotFoundException("UniversityDTO getUniversity")));
    }

    public UniversityDTO createUniversity(UniversityDTO universityDTO) {
        return universityMapper
                        .toDTO(universityRepository
                        .save(universityMapper.toEntity(universityDTO)));
    }

    public UniversityDTO updateUniversity(Long id, UniversityDTO universityDTO) throws UniversityNotFoundException {
        University university;
        university = universityRepository.findById(id)
                    .orElseThrow(() -> new UniversityNotFoundException("UniversityDTO updateUniversity"));

        university.setName(universityDTO.getName());
        university.setRating(universityDTO.getRating());
        university.setProfilePicture(universityDTO.getProfilePicture());

        University updated = universityRepository.save(university);
        return universityMapper.toDTO(updated);
    }

    public void deleteUniversity(Long id) throws UniversityNotFoundException {
        if (!universityRepository.existsById(id)) {
            throw new UniversityNotFoundException("void deleteUniversity");
        }

        universityRepository.deleteById(id);
    }

    public ReviewDTO addReview(Long universityId, ReviewDTO reviewDTO, String username) throws UniversityNotFoundException {
        University university = null;
        university = universityRepository.findById(universityId)
                    .orElseThrow(() -> new UniversityNotFoundException("UniversityDTO addReview"));

        userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        Review review = reviewMapper.toEntity(reviewDTO);
        review.setCreatedAt(review.getCreatedAt());
        review.setAuthor(userRepository.findByUsername(username).get());
        review.setReviewSubject(university);
        review.setApproved(false);

        university.getReviews().add(review);

        universityRepository.save(university);

        return reviewMapper.toDTO(review);
    }
}