package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Tienda;
import com.example.demo.services.TiendaService;
import com.example.demo.services.TiendaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/tiendas")
@RequiredArgsConstructor
public class TiendaController {

    private final TiendaService service;

    @GetMapping
    public String lista (Model model) {
        model.addAttribute("lista", service.sel()); 
        model.addAttribute("tienda", new Tienda());
        return "admin/tiendas";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute Tienda tienda) {
        service.insertUpdate(tienda);        
        return "redirect:/admin/tiendas";
    }
    
    @GetMapping("/edit")
    public String editar(@RequestParam("id") Long id, Model model){
        model.addAttribute("tienda", service.selectOne(id));
        model.addAttribute("lista", service.sel());
        return "admin/tiendas";
    }
    @PostMapping("/delete")
    public String eliminar(@RequestParam("id") Long id){
        service.delete(id);
        return "redirect:/admin/tiendas";
    }
}
