package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Producto;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.ProductoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final CategoriaService cService;
    private final ProductoService service;

    @GetMapping
    public String lista (Model model) {
        model.addAttribute("lista", service.sel()); //Lista de todas las Productos y las enviare al HTML con el alias "Productos"
        model.addAttribute("producto", new Producto());
        model.addAttribute("categoria", cService.Sel());
        return "admin/productos";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute Producto producto) {
        service.insertUpdate(producto);
        return "redirect:/admin/productos";
    }
    
    @GetMapping("/edit")
    public String editar(@RequestParam("id") int id, Model model){
        model.addAttribute("producto", service.selectOne(id));
        model.addAttribute("lista", service.sel());
        model.addAttribute("categoria", cService.Sel());
        return "admin/productos";
    }
    @PostMapping("/delete")
    public String eliminar(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/admin/productos";
    }
}
