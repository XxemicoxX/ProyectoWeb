package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.services.CategoriaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService service;

    public String listaCategorias (Model model) {
        model.addAttribute("lista", service.categoriaSel()); //Lista de todas las categorias y las enviare al HTML con el alias "categorias"
        return "categorias";
    }
}
