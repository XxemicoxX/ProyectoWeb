package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.services.TiendaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/tiendas")
@RequiredArgsConstructor
public class TiendaController {
    private final TiendaService service;

    @GetMapping
    public String listaTiendas (Model model) {
        model.addAttribute("lista", service.tiendaSel());
        return "public/sedes";
    }
}
