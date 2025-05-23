package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Tienda;
import com.example.demo.repositories.TiendaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TiendaService {
    private final TiendaRepository repository;

    public List<Tienda> tiendaSel(){
        return repository.findAll();
    }
}
