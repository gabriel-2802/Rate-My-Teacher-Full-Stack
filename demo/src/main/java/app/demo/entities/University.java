package app.demo.entities;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "universities")
public class University extends ReviewSubject {
    private String city;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Teacher> teachers = new ArrayList<Teacher>();
}
