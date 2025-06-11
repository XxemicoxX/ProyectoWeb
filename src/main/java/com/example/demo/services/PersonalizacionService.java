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
<<<<<<< HEAD

    public Personalizacion personalizacionSelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    //INSERT into rol && UPDATE dinosaurio SET
    public Personalizacion personalizacionInsertUpdate (Personalizacion personalizacion) {
        return repository.save(personalizacion);
    }

    public void personalizacionDelete (Integer id) {
        repository.deleteById(id);
    }
=======
>>>>>>> af72320a150b2e350cc86951737a40e68a4908b7
}
