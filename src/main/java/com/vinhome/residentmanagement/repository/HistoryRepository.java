package com.vinhome.residentmanagement.repository;

import com.vinhome.residentmanagement.entity.Gate;
import com.vinhome.residentmanagement.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
