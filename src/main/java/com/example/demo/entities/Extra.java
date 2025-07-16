package com.example.demo.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "extras")
@Data
@NoArgsConstructor
public class Extra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_extra")
    private Long id;
    @NotBlank(message = "El nombre es un campo obligatirio")
    private String nombre;
    @NotNull(message = "El precio es un campo obligatorio")
    @Min(1)
    private BigDecimal precio;
    @NotBlank(message = "El estado es un campo obligatorio")
    private String estado;
}
