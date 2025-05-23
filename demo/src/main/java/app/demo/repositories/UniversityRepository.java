package app.demo.repositories;

import app.demo.entities.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {
    @Query("SELECT u FROM University u LEFT JOIN FETCH u.teachers")
    List<University> findAllWithTeachers();

    @Query("SELECT u FROM University u LEFT JOIN FETCH u.teachers WHERE u.id = :id")
    Optional<University> findByIdWithTeachers(@Param("id") Long id);

    Optional<University> findByName(String name);
    Optional<University> findById(Long id);
    boolean existsByName(String name);
    boolean existsById(Long id);

    University getByName(String name);

    @Query("SELECT u FROM University u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<University> findByNameContainingIgnoreCase(@Param("name") String name);
}