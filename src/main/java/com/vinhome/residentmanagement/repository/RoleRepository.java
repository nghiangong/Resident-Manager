package com.vinhome.residentmanagement.repository;

import com.vinhome.residentmanagement.entity.Gate;
import com.vinhome.residentmanagement.entity.Role;
import com.vinhome.residentmanagement.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
