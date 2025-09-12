package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Role;
import org.eustache.employemanagement.models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleType name);
}
