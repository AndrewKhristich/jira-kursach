package com.jira.demo.dto;

import com.jira.demo.model.TaskProfile;
import lombok.Data;

@Data
public class TaskCreateDto {

    private String name;
    private Long estimate;
    private TaskProfile profile;
    private String description;

}
