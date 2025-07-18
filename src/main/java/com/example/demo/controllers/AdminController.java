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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.PedidoForm;
import com.example.demo.entities.Categoria;
import com.example.demo.entities.Extra;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Producto;
import com.example.demo.entities.Tienda;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.ExtraService;
import com.example.demo.services.PedidoService;
import com.example.demo.services.ProductoService;
import com.example.demo.services.TiendaService;
import com.example.demo.services.UsuarioService;
import com.example.demo.util.RolEnum;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ProductoService pservice;
    private final TiendaService tservice;
    private final CategoriaService cservice;
    private final UsuarioService uservice;
    private final ExtraService extraS;
    private final PedidoService pedidoS;
    private final PasswordEncoder encoder;

    // PRODUCTOS
    @GetMapping("/productos")
    public String listaProductos(@RequestParam(name = "buscar", required = false) String buscar,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Producto> productosPage;

        if (buscar != null && !buscar.isEmpty()) {
            productosPage = pservice.buscarPorNombre(buscar, pageable);
        } else {
            productosPage = pservice.obtenerTodos(pageable);
        }

        model.addAttribute("lista", productosPage.getContent());
        model.addAttribute("page", productosPage);
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", cservice.selActivas());
        model.addAttribute("usuarios", uservice.sel());
        model.addAttribute("buscar", buscar);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);
        model.addAttribute("usuarioLogueado", usuarioLogueado);

        return "admin/productos";
    }

    @PostMapping("productos/toggleEstadoProductos")
    public String toggleEstadoProductos(@RequestParam("id") Long id) {
        Producto producto = pservice.selectOne(id);
        if ("activo".equalsIgnoreCase(producto.getEstado())) {
            producto.setEstado("desactivado");
        } else {
            producto.setEstado("activo");
        }
        pservice.insertUpdate(producto);

        return "redirect:/admin/productos";
    }

    @GetMapping("/tiendas")
    public String listaTiendas(@RequestParam(name = "buscar", required = false) String buscar,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 2);
        Page<Tienda> tiendasPage;

        if (buscar != null && !buscar.isBlank()) {
            tiendasPage = tservice.buscarPorNombre(buscar, pageable);
        } else {
            tiendasPage = tservice.obtenerTodos(pageable);
        }

        model.addAttribute("page", tiendasPage);
        model.addAttribute("lista", tiendasPage.getContent());
        model.addAttribute("buscar", buscar);
        model.addAttribute("tienda", new Tienda());
        model.addAttribute("usuarios", uservice.sel());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);
        model.addAttribute("usuarioLogueado", usuarioLogueado);

        return "admin/tiendas";
    }

    @PostMapping("tiendas/toggleEstadoTiendas")
    public String toggleEstadoTiendas(@RequestParam("id") Long id) {
        Tienda tienda = tservice.selectOne(id);
        if (tienda != null) {
            if ("activo".equalsIgnoreCase(tienda.getEstado())) {
                tienda.setEstado("desactivado");
            } else {
                tienda.setEstado("activo");
            }
            tservice.insertUpdate(tienda);
        }
        return "redirect:/admin/tiendas";
    }

    @GetMapping("/categorias")
    public String listaCategoria(@RequestParam(name = "buscar", required = false) String buscar,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 2);
        Page<Categoria> categoriaPage;

        if (buscar != null && !buscar.isBlank()) {
            categoriaPage = cservice.buscarPorNombre(buscar, pageable);
        } else {
            categoriaPage = cservice.obtenerTodos(pageable);
        }

        model.addAttribute("page", categoriaPage);
        model.addAttribute("lista", cservice.sel());
        model.addAttribute("buscar", buscar);
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("usuarios", uservice.sel());

        // Usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);
        model.addAttribute("usuarioLogueado", usuarioLogueado);

        return "admin/categorias";
    }

    @PostMapping("categorias/toggleEstadoCategorias")
    public String toggleEstadoCategorias(@RequestParam("id") Long id) {
        Categoria categoria = cservice.selectOne(id);
        if (categoria != null) {
            if ("activo".equalsIgnoreCase(categoria.getEstado())) {
                categoria.setEstado("desactivado");
            } else {
                categoria.setEstado("activo");
            }
            cservice.insertUpdate(categoria);
        }
        return "redirect:/admin/categorias";
    }

    // USUARIOS
    @GetMapping("/usuarios")
    public String lista(
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) String filtro_rol,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 3);
        Page<Usuario> usuariosPage = uservice.filtrarUsuarios(buscar, filtro_rol, pageable);

        model.addAttribute("page", usuariosPage);
        model.addAttribute("lista", usuariosPage.getContent());
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("tiendas", tservice.sel());
        model.addAttribute("rolesDisponibles", RolEnum.values());
        model.addAttribute("filtro_rol", filtro_rol);
        model.addAttribute("buscar", buscar);

        // Usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !"anonymousUser".equals(auth.getName())) {
            Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(auth.getName());
            model.addAttribute("usuarioLogueado", usuarioLogueado);
        }

        return "admin/usuarios";
    }

    @PostMapping("/usuarios/save")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult result, Model model) {
        model.addAttribute("lista", uservice.sel());
        model.addAttribute("tiendas", tservice.sel());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);
        model.addAttribute("usuarioLogueado", usuarioLogueado);

        if (result.hasErrors()) {
            return "admin/usuarios";
        }

        try {
            usuario.setRol(RolEnum.EMPLOYEE);
            usuario.setContrasena(encoder.encode(usuario.getContrasena()));
            uservice.crearUsuario(usuario);
            return "redirect:/admin/usuarios";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el usuario: " + e.getMessage());
            return "admin/usuarios";
        }
    }

    @GetMapping("usuarios/edit")
    public String editarUsuario(@RequestParam("id") Long id, Model model) {
        model.addAttribute("usuario", uservice.selectOne(id));
        model.addAttribute("lista", uservice.sel());
        model.addAttribute("tiendas", tservice.selActivas());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/usuarios";
    }

    @PostMapping("usuarios/delete")
    public String eliminarUsuario(@RequestParam("id") Long id) {
        uservice.delete(id);
        return "redirect:/admin/usuarios";
    }

    @PostMapping("usuarios/cambiarEstado")
    public String cambiarEstado(@RequestParam("id") Long id) {
        Usuario usuario = uservice.selectOne(id);

        if ("activo".equals(usuario.getEstado())) {
            usuario.setEstado("desactivado");
        } else {
            usuario.setEstado("activo");
        }

        uservice.insertUpdate(usuario);
        return "redirect:/admin/usuarios";
    }

    // EXTRAS
    @GetMapping("/extras")
    public String listaExtra(@RequestParam(name = "buscar", required = false) String buscar,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Extra> extrasPage;

        if (buscar != null && !buscar.isBlank()) {
            extrasPage = extraS.buscarPorNombre(buscar, pageable);
        } else {
            extrasPage = extraS.obtenerTodos(pageable);
        }

        model.addAttribute("page", extrasPage);
        model.addAttribute("lista", extrasPage.getContent());
        model.addAttribute("buscar", buscar);
        model.addAttribute("extra", new Extra());
        model.addAttribute("usuarios", uservice.sel());

        // Usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);
        model.addAttribute("usuarioLogueado", usuarioLogueado);

        return "admin/extras";
    }

    @PostMapping("extras/toggleEstadoExtras")
    public String toggleEstadoExtras(@RequestParam("id") Long id) {
        Extra extra = extraS.selectOne(id);
        if (extra != null) {
            if ("activo".equalsIgnoreCase(extra.getEstado())) {
                extra.setEstado("desactivado");
            } else {
                extra.setEstado("activo");
            }
            extraS.insertUpdate(extra);
        }
        return "redirect:/admin/extras";
    }

    @GetMapping("/pedidos")
    public String listarVentas(Model model) {
        model.addAttribute("lista", pedidoS.sel());
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("usuarios", uservice.selActivas());
        model.addAttribute("categorias", cservice.selActivas());
        model.addAttribute("productos", pservice.selActivasConCategoria());
        model.addAttribute("extras", extraS.selActivas());
        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/pedidos";
    }

    // 🔹 Guardar nuevo pedido (pedido con detalles)
    @PostMapping("pedidos/save")
    public String guardarVenta(@ModelAttribute PedidoForm pedidoForm, Model model) {
        try {
            // Obtener el correo del usuario autenticado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();

            // Buscar el usuario por su correo
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
            model.addAttribute("categorias", cservice.selActivas());
            model.addAttribute("productos", pservice.selActivas());
            model.addAttribute("extras", extraS.selActivas());

            // Obtener usuario logueado para mostrar en caso de error
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();
            Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);
            model.addAttribute("usuarioLogueado", usuarioLogueado); 

            return "admin/pedidos";
        }

        return "redirect:/admin/pedidos";
    }

    // 🔹 Eliminar pedido
    @PostMapping("pedidos/delete")
    public String eliminarVenta(@RequestParam("id") Long id) {
        pedidoS.delete(id);
        return "redirect:/admin/pedidos";
    }

    // HISTORIAL
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

    // 🔹 Ver detalles específicos de un pedido
    @GetMapping("/pedidos/{id}")
    public String verDetallePedido(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoS.selectOne(id);
        if (pedido == null) {
            return "redirect:/admin/historial";
        }

        model.addAttribute("pedido", pedido);

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);
        model.addAttribute("usuarioLogueado", usuarioLogueado);

        return "admin/detalle-pedido";
    }
}
