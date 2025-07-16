package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Categoria;
import com.example.demo.entities.Producto;
import com.example.demo.entities.Tienda;
import com.example.demo.repositories.TiendaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TiendaService {
    @Autowired
    private TiendaRepository repository;
    
    public List<Tienda> sel() {
        return repository.findAll();
    }

    public Tienda selectOne(Long id) {
        return repository.findById(id).orElse(null);
    }
    
    public Tienda insertUpdate (Tienda tienda) {
        if (tienda.getEstado() == null || tienda.getEstado().isEmpty()) {
            tienda.setEstado("activo");
        }
        return repository.save(tienda);
    }

    public List<Tienda> selActivas() {
        return repository.findByEstado("activo");
    }

    public void delete (Long id) {
        repository.deleteById(id);
    }

    public Page<Tienda> obtenerTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Tienda> buscarPorNombre(String nombre, Pageable pageable) {
        return repository.findByNombreContainingIgnoreCase(nombre, pageable);
    }
}
