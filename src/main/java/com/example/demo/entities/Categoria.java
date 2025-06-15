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
    @Column(name = "id_categoria")
<<<<<<< HEAD
    private Long id;
    @NotBlank(message = "El nombre es un campo obligatorio")
=======
    private Long idCategoria;
>>>>>>> ad6c9b116df4bc0ad7cd847fa47184870f57b33d
    private String nombre;
    @NotBlank(message = "Ingrese una descripcion para la categoia")
    private String descripcion;
}
