package app.demo.entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @TableGenerator(
            name = "global_id_generator",
            table = "id_sequence",
            pkColumnName = "entity_name",
            valueColumnName = "next_id_value",
            pkColumnValue = "review",
            initialValue = 30000,
            allocationSize = 50
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "global_id_generator")
    @Column(name = "review_id")
    private Long id;

    private Double rating;
    private String content;
    private Date createdAt;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_subject_id", nullable = false)
    private ReviewSubject reviewSubject;
}
