package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Tienda;
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
        return repository.save(tienda);
    }

    public void delete (Long id) {
        repository.deleteById(id);
    }
}
