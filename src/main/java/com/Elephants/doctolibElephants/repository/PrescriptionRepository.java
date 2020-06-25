package com.Elephants.doctolibElephants.repository;

import com.Elephants.doctolibElephants.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query(nativeQuery = true, value= "SELECT DISTINCT * FROM prescription"+
            " JOIN ordonnance ON prescription.ordonnance_id = ordonnance.id"+
            " WHERE patient_id = :id")
    List<Prescription> findByPatientId(@Param("id") Long id);
}
