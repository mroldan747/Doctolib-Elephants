package com.Elephants.doctolibElephants.repository;

import com.Elephants.doctolibElephants.entity.FollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp, Long> {

    List<FollowUp> findAllByPrescriptionIdAndDay(Long prescriptionId, Integer day);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM follow_up WHERE prescription_id = :id")
    public Integer totalFollowUp(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM follow_up WHERE status = 1 AND prescription_id = :id")
    public Integer totalStatus1(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM follow_up WHERE status = 2 AND prescription_id = :id")
    public Integer totalStatus2(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM follow_up WHERE status = 3 AND prescription_id = :id")
    public Integer totalStatus3(@Param("id") Long id);


}

