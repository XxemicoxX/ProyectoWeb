package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Personalizacion;
import com.example.demo.services.PersonalizacionService;

import lombok.RequiredArgsConstructor;
@Controller
@RequestMapping("admin/personalizacion")
@RequiredArgsConstructor
public class PersonalizacionController {

    private final PersonalizacionService service;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("lista", service.personalizacionSel()); 
        model.addAttribute("personalizacion", new Personalizacion());
        return "admin/personalizaciones";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute Personalizacion personalizacion) {
        service.personalizacionInsertUpdate(personalizacion);        
        return "redirect:/admin/personalizaciones";
    }
    
    @GetMapping("/edit")
    public String editar(@RequestParam("id") int id, Model model){
        model.addAttribute("personalizacion", service.personalizacionSelectOne(id));
        model.addAttribute("lista", service.personalizacionSel());
        return "admin/personalizaciones";
    }
    @PostMapping("/delete")
    public String eliminar(@RequestParam("id") Integer id){
        service.personalizacionDelete(id);
        return "redirect:/admin/personalizaciones";
=======
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
>>>>>>> af72320a150b2e350cc86951737a40e68a4908b7
    }
}
