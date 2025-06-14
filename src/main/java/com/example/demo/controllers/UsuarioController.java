package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Usuario;
import com.example.demo.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @GetMapping
    public String lista (Model model) {
        model.addAttribute("lista", service.sel()); 
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute Usuario usuario) {
        service.insertUpdate(usuario);        
        return "redirect:/admin/usuarios";
    }
    
    @GetMapping("/edit")
    public String editar(@RequestParam("id") Long id, Model model){
        model.addAttribute("usuario", service.selectOne(id));
        model.addAttribute("lista", service.sel());
        return "admin/usuarios";
    }
    @PostMapping("/delete")
    public String eliminar(@RequestParam("id") Long id){
        service.delete(id);
        return "redirect:/admin/usuarios";
    }
}
