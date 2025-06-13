package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    @Column(name = "id_categoria")
    private Integer id;
    @Column(name = "nombre_categoria")
    private String nombre;
    private String descripcion;
=======
    private Integer id_categoria;
    @NotBlank(message = "Eliga la categoria coreespondiente")
    private String nombre;
>>>>>>> 2336c3570793558e31cb2df23d9277932d9e7ae8
}
