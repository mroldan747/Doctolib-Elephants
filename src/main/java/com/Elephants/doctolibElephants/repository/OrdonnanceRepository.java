package com.Elephants.doctolibElephants.repository;

import com.Elephants.doctolibElephants.entity.Ordonnance;
import com.Elephants.doctolibElephants.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdonnanceRepository extends JpaRepository<Ordonnance, Long> {




}
