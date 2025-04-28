package app.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course extends ReviewSubject {

    private String description;
}
