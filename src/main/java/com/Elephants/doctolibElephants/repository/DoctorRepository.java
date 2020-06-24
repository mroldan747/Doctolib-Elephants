package com.Elephants.doctolibElephants.repository;

import com.Elephants.doctolibElephants.entity.Doctor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
