package com.vinhome.residentmanagement.repository;

import com.vinhome.residentmanagement.entity.Gate;
import com.vinhome.residentmanagement.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.deletedAt IS NULL AND u.house.id = :houseId")
    boolean livingPeopleExist(Long houseId);

    @Query("select h from House h WHERE h.deletedAt is null")
    List<House> findAllHouses();
}
