package com.vinhome.residentmanagement.repository;

import com.vinhome.residentmanagement.entity.History;
import com.vinhome.residentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
