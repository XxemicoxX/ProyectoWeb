package com.example.demo.entities;

import com.example.demo.util.RolEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotBlank(message = "El nombre es un campo obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Email(message = "Ingrese un correo válido (ej: ejemplo@correo.com)")
    @NotBlank(message = "El correo es obligatorio")
    @Column(nullable = false, length = 100)
    private String correo;

    @NotBlank(message = "Ingrese una contraseña valida")
    private String contrasena;

    @NotNull(message = "Ingrese el numero de telefono del usuario")    
    @Pattern(regexp = "^[0-9]{9}$", message = "El telefono debe de tener 9 digitos")
     @Column(nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 10, max = 100,message = "La direccion debe de tener entre 10 a 100 caracterres")
    @Column(nullable = false, length = 200)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolEnum rol;

}
