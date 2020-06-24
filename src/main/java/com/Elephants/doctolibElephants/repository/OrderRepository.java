package com.Elephants.doctolibElephants.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderRepository, Long> {
}
