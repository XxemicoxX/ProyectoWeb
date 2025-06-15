package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Categoria;
import com.example.demo.repositories.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

   private final CategoriaRepository repository;

    public List<Categoria> sel() {
        return repository.findAll();
    }

    public Categoria selectOne (Long id) {
        return repository.findById(id).orElse(null);
    }

    //INSERT into rol && UPDATE dinosaurio SET
    public Categoria insertUpdate (Categoria categoria) {
        return repository.save(categoria);
    }

    public void delete (Long id) {
        repository.deleteById(id);
    }
}
