package com.example.demo.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_extra")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"detallePedido", "extra"})
public class DetalleExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_extra")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "detalle_pedido")
    private DetallePedido detallePedido;

    @ManyToOne
    @JoinColumn(name = "id_extra")
    private Extra extra;
    @NotNull(message = "La cantidad no puede ser nula")
    @Max(5)
    @Min(0)
    private Integer cantidad;
}
