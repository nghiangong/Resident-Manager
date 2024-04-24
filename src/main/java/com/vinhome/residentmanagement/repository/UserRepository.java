package com.vinhome.residentmanagement.repository;

import com.vinhome.residentmanagement.entity.History;
import com.vinhome.residentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("select u from User u where (u.ownId = :ownId or u.id = :ownId) and u.deletedAt is null")
    List<User> getFamilyMembers(Long ownId);

    @Query("select u from User u " +
            "right join u.roles r " +
            "WHERE r.name = 'ROLE_USER' " +
            "AND u.acceptedStatus = :acceptedStatus " +
            "AND (:keyword is null or concat(u.name, COALESCE(u.email, ''), u.phone, u.username, u.house.name) LIKE %:keyword% ) " +
            "AND u.deletedAt is null")
    List<User> findAllResidents(boolean acceptedStatus, String keyword);

    @Query("select count (u) from User u  right join u.roles r WHERE r.name = 'ROLE_USER' AND u.acceptedStatus = true and u.deletedAt is not null")
    Long countLeavedResidents();

    @Query("select u from User u " +
            "right join u.roles r " +
            "WHERE r.name = 'ROLE_GATEKEEPER' " +
            "AND (:keyword is null or concat(u.name, COALESCE(u.email, ''), u.phone, u.username, u.gate.name) LIKE %:keyword% ) " +
            "AND (:acceptedStatus IS NULL OR u.acceptedStatus = :acceptedStatus) " +
            "AND u.deletedAt IS NULL")
    List<User> findAllGateKeepers(Boolean acceptedStatus, String keyword);
}
