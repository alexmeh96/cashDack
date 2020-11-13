package com.coder.resourceserver.repo;


import com.coder.resourceserver.model.ERole;
import com.coder.resourceserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
