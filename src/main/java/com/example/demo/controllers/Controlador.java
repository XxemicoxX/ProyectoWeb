package com.example.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class Controlador {

    @GetMapping("/contacto")
    public String contacto() {
        return "public/contacto";
    }

    @GetMapping("/formulario")
    public String formulario() {
        return "public/formulario";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "public/nosotros";
    }

}