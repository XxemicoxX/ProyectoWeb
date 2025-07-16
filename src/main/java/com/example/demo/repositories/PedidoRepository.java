package com.example.demo.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Pedido;
import com.example.demo.entities.Producto;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
       @Query("SELECT p FROM Producto p JOIN FETCH p.categoria")
       List<Producto> findAllWithCategoria();

       // Consulta para obtener todos los pedidos con sus relaciones cargadas
       @Query("SELECT DISTINCT p FROM Pedido p " +
                     "LEFT JOIN FETCH p.usuario " +
                     "LEFT JOIN FETCH p.detalles d " +
                     "LEFT JOIN FETCH d.producto " +
                     "LEFT JOIN FETCH d.extras e " +
                     "LEFT JOIN FETCH e.extra " +
                     "ORDER BY p.fecha DESC")
       List<Pedido> findAllWithDetails();

       // Consulta para obtener pedidos por usuario
       @Query("SELECT DISTINCT p FROM Pedido p " +
                     "LEFT JOIN FETCH p.usuario " +
                     "LEFT JOIN FETCH p.detalles d " +
                     "LEFT JOIN FETCH d.producto " +
                     "LEFT JOIN FETCH d.extras e " +
                     "LEFT JOIN FETCH e.extra " +
                     "WHERE p.usuario.id = :usuarioId " +
                     "ORDER BY p.fecha DESC")
       List<Pedido> findByUsuarioIdWithDetails(@Param("usuarioId") Long usuarioId);

       // Consulta para obtener pedidos por rango de fechas
       @Query("SELECT DISTINCT p FROM Pedido p " +
                     "LEFT JOIN FETCH p.usuario " +
                     "LEFT JOIN FETCH p.detalles d " +
                     "LEFT JOIN FETCH d.producto " +
                     "LEFT JOIN FETCH d.extras e " +
                     "LEFT JOIN FETCH e.extra " +
                     "WHERE p.fecha BETWEEN :fechaInicio AND :fechaFin " +
                     "ORDER BY p.fecha DESC")
       List<Pedido> findByFechaRangeWithDetails(@Param("fechaInicio") LocalDate fechaInicio,
                     @Param("fechaFin") LocalDate fechaFin);

       // Consulta para obtener un pedido específico con todos sus detalles
       @Query("SELECT DISTINCT p FROM Pedido p " +
                     "LEFT JOIN FETCH p.usuario " +
                     "LEFT JOIN FETCH p.detalles d " +
                     "LEFT JOIN FETCH d.producto " +
                     "LEFT JOIN FETCH d.extras e " +
                     "LEFT JOIN FETCH e.extra " +
                     "WHERE p.id = :id")
       Optional<Pedido> findByIdWithDetails(@Param("id") Long id);

       @Query("""
                         SELECT p FROM Pedido p
                         WHERE (:usuarioId IS NULL OR p.usuario.id = :usuarioId)
                           AND (:fecha IS NULL OR DATE(p.fecha) = :fecha)
                           AND (
                             :filtro IS NULL OR
                             LOWER(p.usuario.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                             CAST(p.id AS string) LIKE CONCAT('%', :filtro, '%')
                           )
                     """)
       Page<Pedido> buscarHistorialConFiltros(
                     @Param("usuarioId") Long usuarioId,
                     @Param("fecha") LocalDate fecha,
                     @Param("filtro") String filtro,
                     Pageable pageable);

}
