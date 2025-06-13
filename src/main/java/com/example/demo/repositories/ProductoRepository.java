package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    
}
