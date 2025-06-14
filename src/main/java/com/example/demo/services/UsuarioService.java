package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Usuario;
import com.example.demo.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;

    public List<Usuario> sel() {
        return repository.findAll();
    }

    public Usuario selectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    //INSERT into rol && UPDATE dinosaurio SET
    public Usuario insertUpdate (Usuario Usuario) {
        return repository.save(Usuario);
    }

    public void delete (Integer id) {
        repository.deleteById(id);
    }
}
