package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleExtraDTO {
    private Long idDetallePedido; // Muy importante para saber a qué producto pertenece
    private Long idExtra;
    private Integer cantidad;
}
