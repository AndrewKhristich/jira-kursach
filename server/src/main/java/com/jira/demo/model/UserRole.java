package com.jira.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_roles")
public class UserRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(name = "user_role", nullable = false)
    private String authority;

    public UserRole() {
    }

    public UserRole(User user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}

