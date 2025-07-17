package com.example.demo.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.Tienda;
import com.example.demo.services.TiendaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/api/tiendas")
@RequiredArgsConstructor
public class TiendasApiController {
    
    private final TiendaService tservice;

     @PostMapping("/save")
    public ResponseEntity<?> guardarTiendas(@Valid @RequestBody Tienda tienda, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        tservice.insertUpdate(tienda);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> editarTiendas(@PathVariable("id") Long id) {
        Tienda tienda = tservice.selectOne(id);
        if (tienda == null) {
            return ResponseEntity.status(404).body("Categoria no encontrada");
        }
        return ResponseEntity.ok(tienda);
    }

}
