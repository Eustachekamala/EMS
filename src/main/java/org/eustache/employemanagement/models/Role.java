package org.eustache.employemanagement.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Enumerated(EnumType.STRING) // store enum name
    @Column(length = 20, nullable = false, unique = true)
    private RoleType name;

    //Relationships
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    // ✅ custom constructor
    public Role(RoleType name) {
        this.name = name;
    }
}

