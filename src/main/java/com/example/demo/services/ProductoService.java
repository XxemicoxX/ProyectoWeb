package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Producto;
import com.example.demo.repositories.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repository;

    public List<Producto> sel() {
        return repository.findAll();
    }

    public Producto selectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    //INSERT into rol && UPDATE dinosaurio SET
    public Producto insertUpdate (Producto Producto) {
        return repository.save(Producto);
    }

    public void delete (Integer id) {
        repository.deleteById(id);
    }
}
