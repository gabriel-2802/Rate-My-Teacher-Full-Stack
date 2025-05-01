package app.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {
    private Long id;
    private String content;
    private Double rating;
    private Date createdAt;
    private String authorUsername;
    private String subjectId;
}
