package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.services.TiendaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("tiendas")
@RequiredArgsConstructor
public class TiendaController {
    private final TiendaService service;

    public String listaTiendas (Model model) {
        model.addAttribute("lista", service.tiendaSel());
        return "tiendas";
    }
}
