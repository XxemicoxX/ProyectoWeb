package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tiendas")
@Data
@NoArgsConstructor
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tienda;
    @NotBlank(message = "El nombre es un campo obligatorio")
    private String nombre;
    @NotBlank(message = "El direccion es un campo obligatorio")
    @Size(min = 10, max = 100,message = "La direccion debe de tener entre 10 a 100 caracterres")
    private String direccion;
}
