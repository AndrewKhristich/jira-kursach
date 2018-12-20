package com.jira.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private Long estimate;
    @Column
    @CreatedDate
    private LocalDateTime createDate;
    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Column
    @Enumerated(EnumType.STRING)
    private  TaskProfile profile;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "author_id")
    private User author;

    @Column
    private String description;
    @OneToMany(cascade = {CascadeType.ALL},
            mappedBy = "task")
    @JsonManagedReference
    private List<Report> reports = new ArrayList<>();
    @Transient
    @JsonIgnore
    private List<SubTask> subTasksList = new ArrayList<>();

    @Column
    private String subTasks;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
            CascadeType.ALL
            },
            mappedBy = "taskList")
    private List<User> users = new ArrayList<>();

    @Access(AccessType.PROPERTY)
    public String getSubTasks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(subTasksList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error mapping");
        }
    }

    @JsonProperty("subTasks")
    public void setSubTasks(String subTasks) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.subTasksList = mapper.readValue(subTasks, List.class);
        } catch (IOException e) {
            throw new RuntimeException("Error mapping");
        }
    }

    @JsonProperty("subTasks")
    public List getActualSubTasks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(getSubTasks(), List.class);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping");
        }
    }

    @JsonProperty("reported")
    public Long reported() {
        Long count = 0L;
        for (Report report : this.reports) {
            count += report.getEstimate();
        }
        return count;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", estimate=" + estimate +
                ", createDate=" + createDate +
                ", status=" + status +
                ", profile=" + profile +
                ", description='" + description + '\'' +
                '}';
    }
}
