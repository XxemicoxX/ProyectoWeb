package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Producto;
import com.example.demo.entities.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long>{
    List<Tienda> findByEstado(String estado);

    Page<Tienda> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
