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
        return "clasicos";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @GetMapping("/espress-caliente")
    public String espressCaliente() {
        return "espress-caliente";
    }

    @GetMapping("/espress-frio")
    public String espressFrio() {
        return "espress-frio";
    }

    @GetMapping("/formulario")
    public String formulario() {
        return "formulario";
    }

    @GetMapping("/frap-matcha")
    public String frapMatcha() {
        return "frap-matcha";
    }

    @GetMapping("/frap-mocha")
    public String frapMocha() {
        return "frap-mocha";
    }

    @GetMapping("/frap-vainilla")
    public String frapVainilla() {
        return "frap-vainilla";
    }

    @GetMapping
    public String inicio() {
        return "index";
    }

    @GetMapping("/keke-chocolate")
    public String kekeChocolate() {
        return "keke-chocolate";
    }

    @GetMapping("/mas-pedidos")
    public String masPedidos() {
        return "mas-pedidos";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        return "menu";
    }

    @GetMapping("/muffin-naranja")
    public String muffinNaranja() {
        return "muffin-naranja";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/sandwich")
    public String sandwich() {
        return "sandwich";
    }

    @GetMapping("/sede")
    public String sede(Model modelo) {
        List<String> ubicacion = new ArrayList<>();
        ubicacion.add("Av. wiesse 2323232, SJL");
        ubicacion.add("Av. escardo 123, San Miguel");
        ubicacion.add("La Perla 345, Callao");
        ubicacion.add("Av. girasoles, Agustino");
        modelo.addAttribute("sedes", ubicacion);
        return "sedes";
    }
    
    @GetMapping("/admin/personalizaciones")
    public String personalizaciones() {
        return "admin/personalizaciones";
    }
    @GetMapping("/admin/productos")
    public String productos() {
        return "admin/productos";
    }
    @GetMapping("/admin/usuarios")
    public String usuarios() {
        return "admin/usuarios";
    }
}