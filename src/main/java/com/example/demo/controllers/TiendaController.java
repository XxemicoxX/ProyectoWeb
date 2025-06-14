package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.services.TiendaService;

@Controller
@RequestMapping("/tiendas")
public class TiendaController {

    private final TiendaService service;


    public TiendaController(TiendaService service) {
        this.service = service;
    }

    @GetMapping
    public String listaTiendas(Model model) {

        model.addAttribute("lista", service.sel());
        return "public/sedes";

    }

}
