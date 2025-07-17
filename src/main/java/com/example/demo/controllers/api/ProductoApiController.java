package com.example.demo.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Producto;
import com.example.demo.services.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/api/productos")
@RequiredArgsConstructor
public class ProductoApiController {

    private final ProductoService pservice;

    @PostMapping("/save")
    public ResponseEntity<?> guardarProducto(@Valid @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        pservice.insertUpdate(producto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> editarProductos(@PathVariable("id") Long id) {
        Producto producto = pservice.selectOne(id);
        if (producto == null) {
            return ResponseEntity.status(404).body("Producto no encontrado");
        }
        return ResponseEntity.ok(producto);
    }

}
