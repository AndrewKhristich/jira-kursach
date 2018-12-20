package com.jira.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {

    private Long taskId;
    private Integer estimate;
    private String description;
    private LocalDateTime from;

}
