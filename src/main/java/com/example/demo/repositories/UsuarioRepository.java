package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Usuario;
import com.example.demo.util.RolEnum;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
       Optional<Usuario> findByCorreo(String correo);

       List<Usuario> findByEstado(String estado);

       Page<Usuario> findByNombreContainingIgnoreCaseAndRol(String nombre, RolEnum rol, Pageable pageable);

       Page<Usuario> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

       Page<Usuario> findByRol(RolEnum rol, Pageable pageable);

}
