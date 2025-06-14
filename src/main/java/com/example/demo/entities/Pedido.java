package com.example.demo.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
public class Pedido {
    @Column(name = "id_pedido")
    private Integer id;
    private LocalDate fecha;
    @ManyToOne
    @Column(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @Column(name = "id_tienda")
    private Tienda tienda;
}
