package com.example.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.example.demo.services.CategoriaService;
import com.example.demo.services.ExtraService;
import com.example.demo.services.PedidoService;
import com.example.demo.services.ProductoService;
import com.example.demo.services.TiendaService;
import com.example.demo.services.UsuarioService;

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

    // PRODUCTOS
    @GetMapping("/productos")
    public String listaProductos(Model model) {
        model.addAttribute("lista", pservice.sel());
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", cservice.selActivas());
        model.addAttribute("usuarios", uservice.sel());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/productos";
    }

    @PostMapping("productos/save")
    public String guardarProductos(@Valid @ModelAttribute Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("lista", pservice.sel());
            return "admin/productos";
        }
        pservice.insertUpdate(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("productos/edit")
    public String editarProductos(@RequestParam("id") Long id, Model model) {
        model.addAttribute("producto", pservice.selectOne(id));
        model.addAttribute("lista", pservice.sel());
        model.addAttribute("categorias", cservice.selActivas());
        model.addAttribute("usuarios", uservice.sel());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/productos";
    }

    @PostMapping("productos/delete")
    public String eliminarProductos(@RequestParam("id") Long id) {
        pservice.delete(id);
        return "redirect:/admin/productos";
    }

    // Cambiar esto en productos
    @PostMapping("productos/toggleEstado")
    public String toggleEstadoProductos(@RequestParam("id") Long id) {
        Producto producto = pservice.selectOne(id);
        if (producto != null) {
            if ("activo".equalsIgnoreCase(producto.getEstado())) {
                producto.setEstado("desactivado");
            } else {
                producto.setEstado("activo");
            }
            pservice.insertUpdate(producto);
        }
        return "redirect:/admin/productos";
    }

    // TIENDAS
    @GetMapping("/tiendas")
    public String listaTiendas(Model model) {
        model.addAttribute("lista", tservice.sel());
        model.addAttribute("tienda", new Tienda());
        model.addAttribute("usuarios", uservice.sel());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/tiendas";
    }

    @PostMapping("tiendas/save")
    public String guardarTiendas(@Valid @ModelAttribute("tienda") Tienda tienda, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("lista", tservice.sel());
            model.addAttribute("usuarios", uservice.sel());

            // Obtener usuario logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();
            Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);
            model.addAttribute("usuarioLogueado", usuarioLogueado);

            return "admin/tiendas"; // Regresa a la misma vista con errores visibles
        }

        tservice.insertUpdate(tienda);
        return "redirect:/admin/tiendas";
    }

    @GetMapping("tiendas/edit")
    public String editarTiendas(@RequestParam("id") Long id, Model model) {
        model.addAttribute("tienda", tservice.selectOne(id));
        model.addAttribute("lista", tservice.sel());
        model.addAttribute("usuarios", uservice.sel());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/tiendas";
    }

    @PostMapping("tiendas/delete")
    public String eliminarTiendas(@RequestParam("id") Long id) {
        tservice.delete(id);
        return "redirect:/admin/tiendas";
    }

    // CATEGORIAS
    @GetMapping("/categorias")
    public String listaCategoria(Model model) {
        model.addAttribute("lista", cservice.sel());
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("usuarios", uservice.sel());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/categorias";
    }

    @PostMapping("categorias/save")
    public String guardarCategoria(@Valid @ModelAttribute Categoria categoria, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("lista", cservice.sel());
            return "admin/categorias";
        }
        cservice.insertUpdate(categoria);
        return "redirect:/admin/categorias";
    }

    @GetMapping("categorias/edit")
    public String editarCategoria(@RequestParam("id") Long id, Model model) {
        model.addAttribute("categoria", cservice.selectOne(id));
        model.addAttribute("lista", cservice.sel());
        model.addAttribute("usuarios", uservice.sel());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/categorias";
    }

    @PostMapping("categorias/delete")
    public String eliminarCategoria(@RequestParam("id") Long id) {
        cservice.delete(id);
        return "redirect:/admin/categorias";
    }

<<<<<<< HEAD
    @PostMapping("categorias/toggleEstadoCategorias")
    public String toggleEstadoCategorias(@RequestParam("id") Long id) {
=======
    // Cambiar esto en categorias
    @PostMapping("categorias/toggleEstado")
    public String toggleEstado(@RequestParam("id") Long id) {
>>>>>>> 9b25af018d1c6770c7ffcaaea56fdabe963176d3
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
    public String lista(Model model) {
        model.addAttribute("lista", uservice.sel());
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("tiendas", tservice.sel());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/usuarios";
    }

    @PostMapping("usuarios/save")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("lista", uservice.sel());
            model.addAttribute("tiendas", tservice.sel());
            return "admin/usuarios";
        }
        uservice.insertUpdate(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("usuarios/edit")
    public String editarUsuario(@RequestParam("id") Long id, Model model) {
        model.addAttribute("usuario", uservice.selectOne(id));
        model.addAttribute("lista", uservice.sel());
        model.addAttribute("tiendas", tservice.sel());

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

    // EXTRAS
    @GetMapping("/extras")
    public String listaExtra(Model model) {
        model.addAttribute("lista", extraS.sel());
        model.addAttribute("extra", new Extra());
        model.addAttribute("usuarios", uservice.sel());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/extras";
    }

    @PostMapping("extras/save")
    public String guardarExtra(@Valid @ModelAttribute Extra extra, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("lista", extraS.sel());
            return "admin/extras";
        }
        extraS.insertUpdate(extra);
        return "redirect:/admin/extras";
    }

    @GetMapping("extras/edit")
    public String editarExtra(@RequestParam("id") Long id, Model model) {
        model.addAttribute("extra", extraS.selectOne(id));
        model.addAttribute("lista", extraS.sel());
        model.addAttribute("usuarios", uservice.sel());

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuarioLogueado = uservice.buscarUsuarioPorCorreo(correo);

        model.addAttribute("usuarioLogueado", usuarioLogueado); // Pasarlo a la vista

        return "admin/extras";
    }

    @PostMapping("extras/delete")
    public String eliminarExtra(@RequestParam("id") Long id) {
        extraS.delete(id);
        return "redirect:/admin/extras";
    }

    // PEDIDOS
    // 🔹 Mostrar formulario y lista de pedidos
    @GetMapping("/pedidos")
    public String listarVentas(Model model) {
        model.addAttribute("lista", pedidoS.sel());
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("usuarios", uservice.sel());
        model.addAttribute("categorias", cservice.selActivas());
        model.addAttribute("productos", pservice.sel());
        model.addAttribute("extras", extraS.sel());
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

    // 🔹 Eliminar pedido
    @PostMapping("pedidos/delete")
    public String eliminarVenta(@RequestParam("id") Long id) {
        pedidoS.delete(id);
        return "redirect:/admin/pedidos";
    }
}
