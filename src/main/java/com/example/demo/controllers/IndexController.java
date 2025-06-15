package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.services.ProductoService;

@Controller
@RequestMapping("/")
public class IndexController {

    private ProductoService productoService;

    public IndexController(ProductoService productoService) {

        this.productoService = productoService;

    }

    @GetMapping
    public String mostrarInicio(Model model) {
        model.addAttribute("productos", productoService.obtenerPrimerosSeis());
        return "public/index";
    }

}
