package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Extra;
import com.example.demo.entities.Producto;

@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long>{
    List<Extra> findByEstado(String estado);

    Page<Extra> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
