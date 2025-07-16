package com.example.demo.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Categoria;
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

    public List<Producto> selCategoria() {
        return repository.findAllWithCategoria(); // <-- Asegura que trae la categoría
    }

    public Producto selectOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Producto insertUpdate(Producto producto) {
        if (producto.getEstado() == null || producto.getEstado().isEmpty()) {
            producto.setEstado("activo");
        }
        return repository.save(producto);
    }

    public List<Producto> selActivas() {
        return repository.findByEstado("activo");
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Producto> obtenerProductosPorCategoria(Long idCategoria) {
        return repository.findByCategoriaIdCategoria(idCategoria);

    }

    public List<Producto> obtenerTop3Productos() {
        return repository.findTop3ByOrderByIdProductoAsc();
    }

    public List<Producto> obtenerClasicos() {
        return repository.findByIdProductoIn(List.of(6, 7));
    }

    public List<Producto> obtenerPrimerosSeis() {
        return repository.findTop6ByOrderByIdProductoAsc();
    }

    public List<Producto> selActivasConCategoria() {
        return repository.findByEstadoWithCategoria("activo");
    }
    public Page<Producto> obtenerTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Producto> buscarPorNombre(String nombre, Pageable pageable) {
        return repository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    public List<Producto> obtenerProductosActivosConCategoriaActiva() {
        return repository.findActivosConCategoriaActiva();
    }

}
