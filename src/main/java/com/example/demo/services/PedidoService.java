package com.example.demo.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.example.demo.repositories.DetalleExtraRepository;
import com.example.demo.repositories.DetallePedidoRepository;
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
    private final DetallePedidoRepository detallePedidoR;
    private final DetalleExtraRepository detalleExtraR;
    private final ProductoRepository productoR;
    private final ExtraRepository extraR;
    private final UsuarioRepository usuarioR;

    @Transactional
    public Pedido crearPedidoConExtras(Long idUsuario, List<DetallePedidoDTO> detallesProductos, List<DetalleExtraDTO> detallesExtras) {
        Usuario usuario = usuarioR.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDate.now());

        BigDecimal totalPedido = BigDecimal.ZERO;
        List<DetallePedido> listaDetallePedidos = new ArrayList<>();

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
            detalle.setExtras(new ArrayList<>()); // Necesario para el cascade

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
}
