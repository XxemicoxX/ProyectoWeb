package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Extra;

@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long>{
    List<Extra> findByEstado(String estado);
}
