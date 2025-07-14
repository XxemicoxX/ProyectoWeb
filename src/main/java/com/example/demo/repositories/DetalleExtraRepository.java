package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.DetalleExtra;

@Repository
public interface DetalleExtraRepository extends JpaRepository<DetalleExtra, Long>{
    
}
