package com.vinhome.residentmanagement.repository;

import com.vinhome.residentmanagement.entity.Gate;
import com.vinhome.residentmanagement.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("SELECT h FROM History h " +
            "WHERE h.deletedAt IS NULL " +
            "AND (:gateId IS NULL OR h.gate.id = :gateId) " +
            "AND (:userId IS NULL OR h.qrCreator.id = :userId) " +
            "AND (:readStatus IS NULL OR h.readStatus = :readStatus ) " +
            "AND (:startDate IS NULL OR (YEAR(h.date) >= YEAR(:startDate) AND MONTH(h.date) >= MONTH(:startDate) AND DAY(h.date) >= DAY(:startDate)) ) " +
            "AND (:endDate IS NULL OR (YEAR(h.date) <= YEAR(:endDate) AND MONTH(h.date) <= MONTH(:endDate) AND DAY(h.date) <= DAY(:endDate)) ) " +
            "AND (:keyword is null or concat(h.name, h.gate.name, h.qrCreator.name) LIKE %:keyword% ) " +
            "ORDER BY h.date DESC")
    List<History> findAllHistories(Long gateId, Long userId, Boolean readStatus, String keyword, Date startDate, Date endDate);

    @Query("SELECT COUNT(h) FROM History h " +
            "WHERE YEAR(h.date) = YEAR(:date) " +
            "AND MONTH(h.date) = MONTH(:date) " +
            "AND DAY(h.date) = DAY(:date) " +
            "and h.deletedAt is null " +
            "AND h.qrCreator.id = :userId")
    Long countEntryExitPerDay(Date date, Long userId);

    @Query("SELECT COUNT(h) FROM History h " +
            "WHERE YEAR(h.date) = :year " +
            "AND MONTH(h.date) = :month " +
            "AND (:day is null or DAY(h.date) = :day) " +
            "and h.deletedAt is null " +
            "AND h.isResident = :isResident")
    Long countVisitorPerMonth(Integer day, int month, int year, boolean isResident);
}
