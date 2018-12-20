package com.jira.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    @JsonBackReference
    private Task task;
    @Column
    @CreatedDate
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime reportedfrom;
    @Column
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    @Column
    private Integer estimate;
    @Column
    private String description;

    @JsonProperty("author")
    public String getAuthorName() {
        return author != null ? author.getUsername() : null;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", reportedfrom=" + reportedfrom +
                ", modifiedDate=" + modifiedDate +
                ", estimate=" + estimate +
                ", description='" + description + '\'' +
                '}';
    }
}
