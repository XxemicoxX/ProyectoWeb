package com.example.demo.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoForm {
    private List<DetallePedidoDTO> detallesProductos;
    private List<DetalleExtraDTO> detallesExtras;
}
