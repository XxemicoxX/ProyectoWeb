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
    private Integer idProducto;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @NotBlank(message = "El nombre es un campo obligatorio")
    private String nombre;

    @NotNull(message = "Ingrese el precio del producto")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @Column(name = "unidad_medida")
    private BigDecimal medida;

    @NotBlank(message = "Coloque el URL de la imagen")
    private String imagen;

    @NotBlank(message = "Ingrese una descripcion del producto ")
    private String descripcion;

    private String estado;
}
