package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Personalizacion;
import com.example.demo.repositories.PersonalizacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonalizacionService {
    private final PersonalizacionRepository repository;

    public List<Personalizacion> personalizacionSel() {
        return repository.findAll();
    }
}
