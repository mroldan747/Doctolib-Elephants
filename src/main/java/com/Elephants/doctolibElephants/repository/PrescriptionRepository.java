package com.Elephants.doctolibElephants.repository;

import com.Elephants.doctolibElephants.entity.Prescription;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}