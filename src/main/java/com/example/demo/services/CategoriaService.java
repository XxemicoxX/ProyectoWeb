package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Categoria;
import com.example.demo.repositories.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

   /**private final CategoriaRepository repository;

    public List<Categoria> Sel() {
        return repository.findAll();
    }

    public Categoria SelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    //INSERT into rol && UPDATE dinosaurio SET
    public Categoria InsertUpdate (Categoria categoria) {
        return repository.save(categoria);
    }

    public void Delete (Integer id) {
        repository.deleteById(id);
    }**/
}
