package com.example.demo.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.DetalleExtraDTO;
import com.example.demo.DTO.DetallePedidoDTO;
import com.example.demo.entities.DetalleExtra;
import com.example.demo.entities.DetallePedido;
import com.example.demo.entities.Extra;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Producto;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.ExtraRepository;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.repositories.ProductoRepository;
import com.example.demo.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private final PedidoRepository pedidoR;
    private final ProductoRepository productoR;
    private final ExtraRepository extraR;
    private final UsuarioRepository usuarioR;

    @Transactional
    public Pedido crearPedidoConExtras(Long idUsuario, List<DetallePedidoDTO> detallesProductos, List<DetalleExtraDTO> detallesExtras) {
        Usuario usuario = usuarioR.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDateTime.now()); // Para timestamp completo

        BigDecimal totalPedido = BigDecimal.ZERO;
        Set<DetallePedido> listaDetallePedidos = new HashSet<>();

        // Mapa temporal para encontrar detalles por un índice (clave lógica)
        Map<Integer, DetallePedido> detalleMap = new HashMap<>();

        // Crear detalles de productos
        for (int i = 0; i < detallesProductos.size(); i++) {
            DetallePedidoDTO dto = detallesProductos.get(i);

            Producto producto = productoR.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(dto.getCantidad());
            detalle.setExtras(new HashSet<>()); // Necesario para el cascade

            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(dto.getCantidad()));
            detalle.setSubtotal(subtotal);

            listaDetallePedidos.add(detalle);
            detalleMap.put(i, detalle); // Guardar con índice lógico
            totalPedido = totalPedido.add(subtotal);
        }

        // Asociar detalles de extras a sus productos
        for (DetalleExtraDTO extraDTO : detallesExtras) {
            Extra extra = extraR.findById(extraDTO.getIdExtra())
                .orElseThrow(() -> new RuntimeException("Extra no encontrado"));

            // Aquí usamos el índice del producto como idDetallePedido (desde JS por ejemplo)
            DetallePedido detalle = detalleMap.get(extraDTO.getIdDetallePedido().intValue());
            if (detalle == null) continue;

            DetalleExtra detalleExtra = new DetalleExtra();
            detalleExtra.setDetallePedido(detalle);
            detalleExtra.setExtra(extra);
            detalleExtra.setCantidad(extraDTO.getCantidad());

            detalle.getExtras().add(detalleExtra);

            BigDecimal extraSubtotal = extra.getPrecio().multiply(BigDecimal.valueOf(extraDTO.getCantidad()));
            detalle.setSubtotal(detalle.getSubtotal().add(extraSubtotal));
            totalPedido = totalPedido.add(extraSubtotal);
        }

        pedido.setTotal(totalPedido);
        pedido.setDetalles(listaDetallePedidos);

        return pedidoR.save(pedido); // Esto guarda todo por cascade
    }

    public List<Pedido> sel() {
        return pedidoR.findAll();
    }

    public Pedido selectOne(Long id) {
        return pedidoR.findById(id).orElse(null);
    }

    public Pedido insertUpdate(Pedido pedido) {
        return pedidoR.save(pedido);
    }    

    public void delete(Long id) {
        pedidoR.deleteById(id);
    }

    public List<Pedido> obtenerTodosLosHistorialPedidos() {
        return pedidoR.findAllWithDetails();
    }

    public List<Pedido> obtenerPedidosPorUsuario(Long usuarioId) {
        return pedidoR.findByUsuarioIdWithDetails(usuarioId);
    }

    public List<Pedido> obtenerPedidosPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return pedidoR.findByFechaRangeWithDetails(fechaInicio, fechaFin);
    }
}
