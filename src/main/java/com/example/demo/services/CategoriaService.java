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

    public List<Categoria> categoriaSel() {
        return repository.findAll();
    }

    public Categoria categoriaSelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    //INSERT into rol && UPDATE dinosaurio SET
    public Categoria categoriaInsertUpdate (Categoria categoria) {
        return repository.save(categoria);
    }

    public void categoriaDelete (Integer id) {
        repository.deleteById(id);
    }
}
