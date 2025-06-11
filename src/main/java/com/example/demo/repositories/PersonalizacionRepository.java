package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Personalizacion;

public interface PersonalizacionRepository extends JpaRepository<Personalizacion, Integer>{
    
}
