package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping
public class Controlador {

    @GetMapping("/clasicos")
    public String clasicos() {
        return "public/clasicos";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "public/contacto";
    }

    @GetMapping("/espress-caliente")
    public String espressCaliente() {
        return "public/espress-caliente";
    }

    @GetMapping("/espress-frio")
    public String espressFrio() {
        return "public/espress-frio";
    }

    @GetMapping("/formulario")
    public String formulario() {
        return "public/formulario";
    }

    @GetMapping("/frap-matcha")
    public String frapMatcha() {
        return "public/frap-matcha";
    }

    @GetMapping("/frap-mocha")
    public String frapMocha() {
        return "public/frap-mocha";
    }

    @GetMapping("/frap-vainilla")
    public String frapVainilla() {
        return "public/frap-vainilla";
    }

    @GetMapping
    public String inicio() {
        return "public/index";
    }

    @GetMapping("/keke-chocolate")
    public String kekeChocolate() {
        return "public/keke-chocolate";
    }

    @GetMapping("/mas-pedidos")
    public String masPedidos() {
        return "public/mas-pedidos";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        return "public/menu";
    }

    @GetMapping("/muffin-naranja")
    public String muffinNaranja() {
        return "public/muffin-naranja";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "public/nosotros";
    }

    @GetMapping("/sandwich")
    public String sandwich() {
        return "public/sandwich";
    }

    @GetMapping("/sede")
    public String sede(Model modelo) {
        List<String> ubicacion = new ArrayList<>();
        ubicacion.add("Av. wiesse 2323232, SJL");
        ubicacion.add("Av. escardo 123, San Miguel");
        ubicacion.add("La Perla 345, Callao");
        ubicacion.add("Av. girasoles, Agustino");
        modelo.addAttribute("sedes", ubicacion);
        return "public/sedes";
    }
}