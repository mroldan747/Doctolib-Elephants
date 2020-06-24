package com.Elephants.doctolibElephants.repository;

import com.Elephants.doctolibElephants.entity.Patient;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
