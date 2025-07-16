package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Categoria;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.UsuarioRepository;
import com.example.demo.util.RolEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    @Autowired
    private final UsuarioRepository repository;

    public Usuario crearUsuario(Usuario usuario) throws Exception {
        if (!repository.findByCorreo(usuario.getCorreo()).isEmpty()) {
            throw new Exception("Usuario ya registrado");
        }
        return repository.save(usuario);
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        return repository.findByCorreo(correo).orElseThrow();
    }

    public List<Usuario> sel() {
        return repository.findAll();
    }

    public Usuario selectOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Usuario insertUpdate(Usuario usuario) {
        if (usuario.getEstado() == null || usuario.getEstado().isEmpty()) {
            usuario.setEstado("activo");
        }
        return repository.save(usuario);
    }

    public List<Usuario> selActivas() {
        return repository.findByEstado("activo");
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Usuario> filtrarUsuarios(String buscar, String filtroRol, Pageable pageable) {
        if ((buscar == null || buscar.isBlank()) && (filtroRol == null || filtroRol.isBlank())) {
            return repository.findAll(pageable); 
        }

        if (buscar != null && !buscar.isBlank() && filtroRol != null && !filtroRol.isBlank()) {
            return repository.findByNombreContainingIgnoreCaseAndRol(buscar, RolEnum.valueOf(filtroRol),
                    pageable);
        }

        if (buscar != null && !buscar.isBlank()) {
            return repository.findByNombreContainingIgnoreCase(buscar, pageable);
        }

        return repository.findByRol(RolEnum.valueOf(filtroRol), pageable);
    }

}
