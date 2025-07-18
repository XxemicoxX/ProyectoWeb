package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Categoria;
import com.example.demo.entities.Producto;
import com.example.demo.repositories.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    @Autowired
    private final CategoriaRepository repository;

    public List<Categoria> sel() {
        return repository.findAll();
    }

    public Categoria selectOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Categoria insertUpdate(Categoria categoria) {
        if (categoria.getEstado() == null || categoria.getEstado().isEmpty()) {
            categoria.setEstado("activo");
        }
        return repository.save(categoria);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Categoria> selActivas() {
        return repository.findByEstado("activo");
    }

    public Page<Categoria> obtenerTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Categoria> buscarPorNombre(String nombre, Pageable pageable) {
        return repository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

}
