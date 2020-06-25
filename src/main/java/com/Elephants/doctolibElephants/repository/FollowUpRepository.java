package com.Elephants.doctolibElephants.repository;

import com.Elephants.doctolibElephants.entity.FollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp, Long> {

    List<FollowUp> findAllByPrescriptionIdAndDay(Long prescriptionId, Integer day);
    Optional<FollowUp> findByPrescriptionIdAndHour(Long prescriptionId, Integer hour);
}

