package com.jira.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GrandRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long taskId;

}
