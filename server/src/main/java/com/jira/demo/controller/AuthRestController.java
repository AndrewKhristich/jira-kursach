package com.jira.demo.controller;

import com.jira.demo.dto.ApiResponse;
import com.jira.demo.dto.JwtAuthenticationResponse;
import com.jira.demo.dto.SignInRequest;
import com.jira.demo.dto.UserCreateDto;
import com.jira.demo.model.User;
import com.jira.demo.repository.UserRepository;
import com.jira.demo.security.CurrentUser;
import com.jira.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
@CrossOrigin(origins = "*")
public class AuthRestController {

    private UserService userService;
    private UserRepository userRepository;

    public AuthRestController(UserService service, UserRepository repository) {
        this.userService = service;
        this.userRepository = repository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("admin")
    public ResponseEntity checkIfAdmin(@CurrentUser User user) {
        if (user.getAuthorities().stream().anyMatch(userRole -> userRole.getAuthority().equals("ADMIN"))){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/check")
    public User checkAuth(@CurrentUser User currentUser) {
        User currentUser1 = currentUser;
        return currentUser1;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody SignInRequest signInRequest) {
        if(userRepository.findByUsername(signInRequest.getUsername()).isPresent()) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        userService.createUser(new UserCreateDto(signInRequest.getUsername(), signInRequest.getPassword()));

        String jwt = userService.authenticate(
                signInRequest.getUsername(), signInRequest.getPassword()
        );

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody SignInRequest signInRequest) {
        String jwt = userService.authenticate(
                signInRequest.getUsername(), signInRequest.getPassword()
        );
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

}
