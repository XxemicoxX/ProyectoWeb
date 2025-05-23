package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.services.PersonalizacionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("personalizaciones")
@RequiredArgsConstructor
public class PersonalizacionController {
    private final PersonalizacionService service;

    public String listaPersonalizacion (Model model) {
        model.addAttribute("lista", service.personalizacionSel());
        return "personalizaciones";
    }
}
