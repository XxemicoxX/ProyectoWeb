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

        Producto producto = productoService.selectOne(id);

        model.addAttribute("producto", producto);

        return "public/producto";
    }

    @GetMapping("/mas-pedidos")
    public String mostrarMasPedidos(Model model) {
        model.addAttribute("productosTop", productoService.obtenerTop3Productos());
        return "public/mas-pedidos";
    }

    @GetMapping("/clasicos")
    public String mostrarClasicos(Model model) {
        model.addAttribute("clasicos", productoService.obtenerClasicos());
        return "public/clasicos";
    }
}
