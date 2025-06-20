package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Categoria;
import com.example.demo.entities.Producto;
import com.example.demo.entities.Tienda;
import com.example.demo.entities.Usuario;
import com.example.demo.services.CategoriaService;
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

     @GetMapping("/productos")
    public String listaProductos (Model model) {
        model.addAttribute("lista", pservice.sel()); 
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", cservice.sel());
        return "admin/productos";
    }

     @PostMapping("productos/save")
    public String guardarProductos(@ModelAttribute Producto producto) {
        pservice.insertUpdate(producto);
        return "redirect:/admin/productos";
    }
    
    @GetMapping("productos/edit")
    public String editarProductos(@RequestParam("id") Long id, Model model){
        model.addAttribute("producto", pservice.selectOne(id));
        model.addAttribute("lista", pservice.sel());
        model.addAttribute("categorias", cservice.sel());
        return "admin/productos";
    }
    @PostMapping("productos/delete")
    public String eliminarProductos(@RequestParam("id") Long id){
        pservice.delete(id);
        return "redirect:/admin/productos";
    }


    @GetMapping("/tiendas")
    public String listaTiendas (Model model) {
        model.addAttribute("lista", tservice.sel()); 
        model.addAttribute("tienda", new Tienda());
        return "admin/tiendas";
    }

    @PostMapping("tiendas/save")
    public String guardarTiendas(@ModelAttribute Tienda tienda) {
        tservice.insertUpdate(tienda);        
        return "redirect:/admin/tiendas";
    }
    
    @GetMapping("tiendas/edit")
    public String editarTiendas(@RequestParam("id") Long id, Model model){
        model.addAttribute("tienda", tservice.selectOne(id));
        model.addAttribute("lista", tservice.sel());
        return "admin/tiendas";
    }
    @PostMapping("tiendas/delete")
    public String eliminarTiendas(@RequestParam("id") Long id){
        tservice.delete(id);
        return "redirect:/admin/tiendas";
    }


    @GetMapping("/categorias")
    public String listaCategoria(Model model) {
        model.addAttribute("lista", cservice.sel()); 
        model.addAttribute("categoria", new Categoria());
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
        return "admin/categorias";
    }

    @PostMapping("categorias/delete")
    public String eliminarCategoria(@RequestParam("id") Long id) {
        cservice.delete(id);
        return "redirect:/admin/categorias";
    }

    @GetMapping("/usuarios")
    public String lista(Model model) {
        model.addAttribute("lista", uservice.sel());
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios";
    }

    @PostMapping("/save")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("lista", uservice.sel());
            return "admin/usuarios";
        }
        uservice.insertUpdate(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/edit")
    public String editarUsuario(@RequestParam("id") Long id, Model model) {
        model.addAttribute("usuario", uservice.selectOne(id));
        model.addAttribute("lista", uservice.sel());
        return "admin/usuarios";
    }

    @PostMapping("/delete")
    public String eliminarUsuario(@RequestParam("id") Long id) {
        uservice.delete(id);
        return "redirect:/admin/usuarios";
    }

}
