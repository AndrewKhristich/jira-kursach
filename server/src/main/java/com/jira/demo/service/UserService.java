package com.jira.demo.service;

import com.jira.demo.dto.UserCreateDto;
import com.jira.demo.model.*;
import com.jira.demo.repository.TaskRepository;
import com.jira.demo.repository.UserRepository;
import com.jira.demo.security.JwtTokenProvider;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private TaskRepository taskRepository;
    private AuthenticationManager authManager;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, TaskRepository taskRepository, AuthenticationManager authManager, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
        this.taskRepository = taskRepository;
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @Transactional
    @javax.transaction.Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("Can not find user by name [%s]", s)
                        )
                );
        return user;
    }

    @Transactional
    @javax.transaction.Transactional
    public User loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                String.format("Can not find user by name [%s]", id)
                        )
                );
        Hibernate.initialize(user.getAuthorities());
        return user;
    }

    public User createUser(UserCreateDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setAuthorities(Collections.singletonList(new UserRole(user, RoleType.USER.name())));
        return userRepository.save(user);
    }

    public User createAdmin() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(encoder.encode("admin"));
        user.setAuthorities(Collections.singletonList(new UserRole(user, RoleType.ADMIN.name())));
        return userRepository.save(user);
    }

    public void assignTaskToUser(Long userId, Long taskId) {
        checkArgument(userRepository.existsById(userId), "No User found with id : {}", userId);
        User user = userRepository.findById(userId).get();
        checkArgument(taskRepository.existsById(taskId), "No Task found with id : {}", userId);
        Task task = taskRepository.findById(taskId).get();
        List<Task> reqTasks = new ArrayList<>();
        user.getTaskList().add(task);
        user.getTaskList().addAll(getAllTasksRecursive(reqTasks, task));
        userRepository.save(user);
    }

    private List<Task> getAllTasksRecursive(List<Task> reqTasks, Task task) {
        for (SubTask subTask : task.getSubTasksList()) {
            Optional<Task> optionalTask = taskRepository.findById(subTask.getId());
            if (!optionalTask.isPresent()) {
                continue;
            }
            reqTasks.add(optionalTask.get());
            reqTasks.addAll(getAllTasksRecursive(reqTasks, optionalTask.get()));
        }
        return reqTasks;
    }

    public String authenticate(String username, String password) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return tokenProvider.generateToken(auth);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
