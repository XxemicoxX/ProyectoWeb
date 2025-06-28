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

    public Usuario crearUsuario(Usuario usuario) throws Exception{
        if (!repository.findByCorreo(usuario.getCorreo()).isEmpty()) {
            throw new Exception("Usuario ya registrado");            
        }
        return repository.save(usuario);
    }

    public Usuario buscarUsuarioPorCorreo(String correo){
        return repository.findByCorreo(correo).orElseThrow();
    }

    public List<Usuario> sel() {
        return repository.findAll();
    }

    public Usuario selectOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Usuario insertUpdate (Usuario usuario) {
        return repository.save(usuario);
    }

    public void delete (Long id) {
        repository.deleteById(id);
    }
}
