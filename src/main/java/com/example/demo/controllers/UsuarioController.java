package com.example.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Usuario;
import com.example.demo.services.UsuarioService;
import com.example.demo.util.RolEnum;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;
    private final PasswordEncoder encoder;

    @GetMapping("registro")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "/public/formulario";
    }

        @PostMapping("registro")
        public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
            if (result.hasErrors()) {
                model.addAttribute("usuario", usuario);
                return "/public/formulario";
            }
            try {
                usuario.setRol(RolEnum.CLIENT);
                usuario.setContrasena(encoder.encode(usuario.getContrasena()));
                service.crearUsuario(usuario);
                return "redirect:/";
            } catch (Exception e) {
                model.addAttribute("usuario", usuario);
                return "/public/formulario";
            }
        }

}
