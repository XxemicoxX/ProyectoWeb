package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Producto;
import com.example.demo.repositories.ProductoRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ProductoService {

    @Autowired
    private ProductoRepository repository;
    
    public List<Producto> sel() {
        return repository.findAll();
    }

    public Producto selectOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    //INSERT into rol && UPDATE dinosaurio SET
    public Producto insertUpdate (Producto producto) {
        return repository.save(producto);
    }

    public void delete (Long id) {
        repository.deleteById(id);
    }
}
