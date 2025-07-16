package com.example.demo.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.PedidoForm;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Usuario;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.ExtraService;
import com.example.demo.services.PedidoService;
import com.example.demo.services.ProductoService;
import com.example.demo.services.TiendaService;
import com.example.demo.services.UsuarioService;
import com.example.demo.util.RolEnum;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UsuarioController {
    private final PasswordEncoder encoder;
    private final ProductoService pservice;
    private final CategoriaService cservice;
    private final UsuarioService uservice;
    private final ExtraService extraS;
    private final PedidoService pedidoS;

    @GetMapping("registro")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "/public/formulario";
    }

    @PostMapping("registro")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "public/formulario";
        }
        try {
            usuario.setRol(RolEnum.CLIENT);
            usuario.setContrasena(encoder.encode(usuario.getContrasena()));
            uservice.crearUsuario(usuario);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("usuario", usuario);
            return "public/formulario";
        }
    }

    @GetMapping("/pedidos")
    public String listarVentas(Model model) {
        model.addAttribute("lista", pedidoS.sel());
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("usuarios", uservice.sel());
        model.addAttribute("categorias", cservice.sel());
        model.addAttribute("productos", pservice.sel());
        model.addAttribute("extras", extraS.sel());
        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado);

        return "admin/pedidos";
    }

    @PostMapping("pedidos/save")
    public String guardarVenta(@ModelAttribute PedidoForm pedidoForm, Model model) {
        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();

            Usuario usuario = uservice.buscarUsuarioPorCorreo(correo);
            if (usuario == null) {
                throw new RuntimeException("Usuario no encontrado en la base de datos");
            }

            // Crear el pedido con las dos listas de detalles
            pedidoS.crearPedidoConExtras(usuario.getId(), pedidoForm.getDetallesProductos(),
                    pedidoForm.getDetallesExtras());

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("lista", pedidoS.sel());
            model.addAttribute("usuarios", uservice.sel());
            model.addAttribute("categorias", cservice.sel());
            model.addAttribute("productos", pservice.sel());
            model.addAttribute("extras", extraS.sel());

            // Obtener usuario logueado para mostrar en caso de error
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();
            Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);
            model.addAttribute("usuarioLogueado", usuarioLogueado);

            return "admin/pedidos";
        }

        return "redirect:/admin/pedidos";
    }

    // 🔹 Editar pedido (opcional, si vas a permitirlo)
    @GetMapping("pedidos/edit")
    public String editarVenta(@RequestParam("id") Long id, Model model) {
        model.addAttribute("pedido", pedidoS.selectOne(id));
        model.addAttribute("lista", pedidoS.sel());
        model.addAttribute("usuarios", uservice.sel());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/pedidos";
    }

    // 🔹 Eliminar pedido
    @PostMapping("pedidos/delete")
    public String eliminarVenta(@RequestParam("id") Long id) {
        pedidoS.delete(id);
        return "redirect:/admin/pedidos";
    }

    @GetMapping("/historial")
    public String mostrarHistorial(
            Model model,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());
        Page<Pedido> pedidos = pedidoS.buscarHistorialConFiltros(usuarioId, fecha, filtro, pageable);

        model.addAttribute("listaPedidos", pedidos);
        model.addAttribute("usuarios", uservice.selActivas());

        // ✅ Evita el NullPointerException
        Map<String, Object> param = new HashMap<>();
        param.put("usuarioId", usuarioId);
        param.put("fecha", fecha);
        param.put("filtro", filtro);
        model.addAttribute("param", param);

        // Usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);
        model.addAttribute("usuarioLogueado", usuarioLogueado);

        return "admin/historial";
    }

}
