package app.demo.services;

import app.demo.dto.ReviewDTO;
import app.demo.dto.TeacherDTO;
import app.demo.entities.Review;
import app.demo.entities.Teacher;
import app.demo.entities.User;
import app.demo.exceptions.TeacherNotFoundException;
import app.demo.exceptions.UserNotFoundException;
import app.demo.mappers.ReviewMapper;
import app.demo.mappers.TeacherMapper;
import app.demo.repositories.TeacherRepository;
import app.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final TeacherMapper teacherMapper;
    private final ReviewMapper reviewMapper;

    public TeacherService(TeacherRepository teacherRepository,
                          UserRepository userRepository,
                          TeacherMapper teacherMapper,
                          ReviewMapper reviewMapper) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.teacherMapper = teacherMapper;
        this.reviewMapper = reviewMapper;
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherMapper.toDTOList(teacherRepository.findAll());
    }

    public TeacherDTO getTeacher(Long id) throws TeacherNotFoundException {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));
        return teacherMapper.toDTO(teacher);
    }

    public TeacherDTO getTeacherByName(String name) throws TeacherNotFoundException {
        Teacher teacher = teacherRepository.findByName(name)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with name: " + name));
        return teacherMapper.toDTO(teacher);
    }

    public List<TeacherDTO> searchTeachersByName(String name) {
        List<Teacher> teachers = teacherRepository.findByNameContainingIgnoreCase(name);
        return teacherMapper.toDTOList(teachers);
    }

    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        Teacher saved = teacherRepository.save(teacher);
        return teacherMapper.toDTO(saved);
    }

    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) throws TeacherNotFoundException {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));

        teacher.setName(teacherDTO.getName());
        teacher.setRating(teacherDTO.getRating());
        teacher.setTeacherPicture(teacherDTO.getTeacherPicture());

        Teacher updated = teacherRepository.save(teacher);
        return teacherMapper.toDTO(updated);
    }

    public void deleteTeacher(Long id) throws TeacherNotFoundException {
        if (!teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }

    @Transactional
    public ReviewDTO addReview(Long teacherId, ReviewDTO reviewDTO, String username) throws TeacherNotFoundException, UserNotFoundException {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));
        User user = null;
        user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        Review review = reviewMapper.toEntity(reviewDTO);
        review.setCreatedAt(new Date());
        review.setAuthor(user);
        review.setReviewSubject(teacher);
        review.setApproved(false);
        teacher.getReviews().add(review);
        teacherRepository.save(teacher);

        return reviewMapper.toDTO(review);
    }
}