package com.Elephants.doctolibElephants.repository;

import com.Elephants.doctolibElephants.entity.FollowUp;
import com.Elephants.doctolibElephants.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
