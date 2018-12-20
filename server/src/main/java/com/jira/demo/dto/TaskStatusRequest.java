package com.jira.demo.dto;

import com.jira.demo.model.TaskStatus;
import lombok.Data;

@Data
public class TaskStatusRequest {

    private Long taskId;
    private TaskStatus status;

}
