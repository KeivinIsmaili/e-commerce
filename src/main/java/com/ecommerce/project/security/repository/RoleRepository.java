package com.ecommerce.project.security.repository;

import com.ecommerce.project.security.model.ERole;
import com.ecommerce.project.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
