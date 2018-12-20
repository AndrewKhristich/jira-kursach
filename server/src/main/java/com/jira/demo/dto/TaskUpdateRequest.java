package com.jira.demo.dto;

import com.jira.demo.model.TaskStatus;
import lombok.Data;

@Data
public class TaskUpdateRequest {

    private TaskStatus status;
    private String description;

}
