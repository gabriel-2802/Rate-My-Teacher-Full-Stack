package app.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "review_subjects")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ReviewSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "review_subject_generator")
    @TableGenerator(
            name = "review_subject_generator",
            table = "id_sequence",
            pkColumnName = "entity_name",
            valueColumnName = "next_id_value",
            pkColumnValue = "review_subject",
            initialValue = 10000,
            allocationSize = 50
    )
    @Column(name = "review_subject_id")
    private Long id;

    private String name;
    private Double rating;

    @OneToMany(mappedBy = "reviewSubject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Double calculateRating() {
        Double sum = 0.0;
        for (Review review : reviews) {
            if (review.isApproved()) {
                sum += review.getRating();
            }
        }
        return sum / reviews.size();
    }
}