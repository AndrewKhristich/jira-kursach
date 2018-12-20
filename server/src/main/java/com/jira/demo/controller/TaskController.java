package com.jira.demo.controller;

import com.jira.demo.dto.ReportDto;
import com.jira.demo.dto.TaskCreateDto;
import com.jira.demo.dto.TaskStatusRequest;
import com.jira.demo.dto.TaskUpdateRequest;
import com.jira.demo.model.Report;
import com.jira.demo.model.RoleType;
import com.jira.demo.model.Task;
import com.jira.demo.model.User;
import com.jira.demo.security.CurrentUser;
import com.jira.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("task")
@CrossOrigin(origins = "*")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("all")
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public Page<Task> findByPage(@RequestParam int page, @RequestParam int size) {
        return taskService.findByPage(PageRequest.of(page, size));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("check/{id}")
    public void checkNotForbidden(@CurrentUser User user, @PathVariable Long id) {
        if (user.getAuthorities().stream()
                .noneMatch(userRole ->
                        userRole.getAuthority().equals(RoleType.ADMIN.name())
                )) {
            taskService.checkNotForbiden(user.getId(), id);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("update/{id}")
    public void update(@RequestBody @Valid TaskUpdateRequest updateObj, @PathVariable Long id, @CurrentUser User user) {
        taskService.checkNotForbiden(user.getId(), id);
        taskService.updateDescr(updateObj, id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping
    public Task create(@RequestBody TaskCreateDto dto, @CurrentUser User user) {
        return taskService.createTask(dto, user.getId());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/{id}")
    public Task createSubTask(@RequestBody TaskCreateDto dto, @CurrentUser User user, @PathVariable Long id) {
        return taskService.createSubTask(dto, user.getId(), id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/status")
    public void changeStatus(@RequestBody TaskStatusRequest request, @CurrentUser User user) throws IllegalAccessException {
        taskService.checkNotForbiden(user.getId(), request.getTaskId());
        taskService.changeStatus(request.getTaskId(), request.getStatus());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/report")
    public Report reportTask(@CurrentUser User user, @RequestBody ReportDto dto) {
        return taskService.reportTask(user.getUsername(), dto);
    }

}
