package com.example.demo.entities;

import jakarta.persistence.Column;
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
<<<<<<< HEAD
    @Column(name = "id_tienda")
    private Integer id;
    private String nombre;
=======
    private Integer id_tienda;
    @NotBlank(message = "El nombre es un campo obligatorio")
    private String nombre;
    @NotBlank(message = "El direccion es un campo obligatorio")
    @Size(min = 10, max = 100,message = "La direccion debe de tener entre 10 a 100 caracterres")
>>>>>>> 2336c3570793558e31cb2df23d9277932d9e7ae8
    private String direccion;
}
