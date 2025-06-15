package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Producto;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService service;
    private final CategoriaService cService;


    @GetMapping("/{id}")
    public String verProducto(@PathVariable Long id, Model model) {

        Producto producto = service.selectOne(id);

        model.addAttribute("producto", producto);

        return "public/producto";

    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("lista", service.sel());
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", cService.sel());
        return "admin/productos";
    }

    @PostMapping("/save")
    public String guardar(@Valid @ModelAttribute Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("lista", service.sel());
            model.addAttribute("categorias", cService.sel());
            return "admin/productos";
        }
        service.insertUpdate(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/edit")
    public String editar(@RequestParam("id") Long id, Model model) {
        model.addAttribute("producto", service.selectOne(id));
        model.addAttribute("lista", service.sel());
        model.addAttribute("categorias", cService.sel());
        return "admin/productos";
    }

    @PostMapping("/delete")
    public String eliminar(@RequestParam("id") Long id) {
        service.delete(id);
        return "redirect:/admin/productos";
    }

    @GetMapping("/clasicos")
    public String mostrarClasicos(Model model) {

        model.addAttribute("clasicos", service.obtenerClasicos());

        return "public/clasicos";

    }

    @GetMapping("/mas-pedidos")
    public String mostrarMasPedidos(Model model) {

        model.addAttribute("productosTop", service.obtenerTop3Productos());

        return "public/mas-pedidos";

    }
}
