package com.example.demo.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Categoria;
import com.example.demo.services.CategoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/api/categorias")
@RequiredArgsConstructor
public class CategoriasApiController {
    
    private final CategoriaService cservice; 

     @PostMapping("/save")
    public ResponseEntity<?> guardarCategorias(@Valid @RequestBody Categoria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        cservice.insertUpdate(categoria);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> editarCategorias(@PathVariable("id") Long id) {
        Categoria categoria = cservice.selectOne(id);
        if (categoria == null) {
            return ResponseEntity.status(404).body("Categoria no encontrada");
        }
        return ResponseEntity.ok(categoria);
    }

}
