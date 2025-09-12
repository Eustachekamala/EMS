package org.eustache.employemanagement.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String email;
    private String password;

    //Relationships
    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // Join table name
            joinColumns = @JoinColumn(name = "user_id"), //FK to user
            inverseJoinColumns = @JoinColumn(name = "role_id") // FK to role
    )
    private Set<Role> roles = new HashSet<>();

    private boolean isActive;
    @CreationTimestamp
    private LocalDate createdAt;
    @UpdateTimestamp
    private LocalDate updatedAt;
    private LocalDate lastLogin;
}
