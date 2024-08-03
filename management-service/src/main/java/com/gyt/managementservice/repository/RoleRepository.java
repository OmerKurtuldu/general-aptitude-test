package com.gyt.managementservice.repository;

import com.gyt.managementservice.model.entities.Role;
import com.gyt.managementservice.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType roleType);

    boolean existsByName(RoleType roleType);
}
