package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Producto;
import com.example.demo.services.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public String verProducto(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerProductoPorId(id);
        model.addAttribute("producto", producto);
        return "public/producto"; 
    }

    /**@PostMapping("/save")
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
    }**/
}
