package com.Elephants.doctolibElephants.repository;

import com.Elephants.doctolibElephants.entity.Ordonnance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Ordonnance, Long> {
    Optional<Ordonnance> findByPatientId(Long id);
}
