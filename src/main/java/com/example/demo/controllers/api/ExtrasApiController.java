package com.example.demo.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.Extra;
import com.example.demo.services.ExtraService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/api/extras")
@RequiredArgsConstructor
public class ExtrasApiController {
     private final ExtraService eService; 

     @PostMapping("/save")
    public ResponseEntity<?> guardarExtras(@Valid @RequestBody Extra extra, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        eService.insertUpdate(extra);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> editarExtras(@PathVariable("id") Long id) {
        Extra extra = eService.selectOne(id);
        if (extra == null) {
            return ResponseEntity.status(404).body("Extra no encontrada");
        }
        return ResponseEntity.ok(extra);
    }
}
