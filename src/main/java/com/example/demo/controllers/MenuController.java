package com.example.demo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Producto;
import com.example.demo.services.ProductoService;

/**
@Controller
@RequestMapping("/menu")
public class MenuController {

    private ProductoService productoService;

    public MenuController(ProductoService productoService) {
        this.productoService = productoService;
    }

    
     @GetMapping
    public String verMenu(Model model) {
        List<Producto> bebidas = productoService.obtenerProductosPorCategoria(1L);
        List<Producto> alimentos = productoService.obtenerProductosPorCategoria(2L);

        model.addAttribute("bebidas", bebidas);
        model.addAttribute("alimentos", alimentos);

        return "public/menu";
    }
}**/
