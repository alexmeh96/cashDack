package com.coder.authserver.repo;

import com.coder.authserver.model.ERole;
import com.coder.authserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
