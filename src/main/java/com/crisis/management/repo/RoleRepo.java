package com.crisis.management.repo;

import com.crisis.management.models.authority.Role;
import com.crisis.management.models.authority.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
