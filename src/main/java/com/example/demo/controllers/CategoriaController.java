package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Categoria;
import com.example.demo.services.CategoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("admin/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("lista", service.sel()); 
        model.addAttribute("categoria", new Categoria());
        return "admin/categorias";
    }

    @PostMapping("/save")
    public String guardar(@Valid @ModelAttribute Categoria categoria, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("lista", service.sel());
            return "admin/categorias";
        }
        service.insertUpdate(categoria);
        return "redirect:/admin/categorias";
    }

    @GetMapping("/edit")
    public String editar(@RequestParam("id") Long id, Model model) {
        model.addAttribute("categoria", service.selectOne(id));
        model.addAttribute("lista", service.sel());
        return "admin/categorias";
    }

    @PostMapping("/delete")
    public String eliminar(@RequestParam("id") Long id) {
        service.delete(id);
        return "redirect:/admin/categorias";
    }
}
