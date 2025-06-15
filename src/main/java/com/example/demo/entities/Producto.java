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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id;
    @NotBlank(message = "El nombre es un campo obligatorio")
    private String nombre;
    // @NotBlank(message = "Ingrese una descripcion del producto ")
    private String descripcion;
    // @Min(1)
    // @NotNull(message = "Ingrese una cantidad")
    private String cantidad;
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;
    private String imagen;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
