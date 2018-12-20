package com.jira.demo.controller;

import com.jira.demo.dto.GrandRequest;
import com.jira.demo.dto.UserCreateDto;
import com.jira.demo.model.User;
import com.jira.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("grand")
    public void grandTaskToUser(@RequestBody @Valid GrandRequest request) {
        userService.assignTaskToUser(request.getUserId(), request.getTaskId());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public void createUser(@RequestBody UserCreateDto dto) {
        userService.createUser(dto);
    }

}
