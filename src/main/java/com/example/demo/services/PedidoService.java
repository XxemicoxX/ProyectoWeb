package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Pedido;
import com.example.demo.repositories.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService{
    private final PedidoRepository repository;

    public List<Pedido> Sel() {
        return repository.findAll();
    }

    public Pedido SelectOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    //INSERT into rol && UPDATE dinosaurio SET
    public Pedido InsertUpdate (Pedido Pedido) {
        return repository.save(Pedido);
    }

    public void Delete (Integer id) {
        repository.deleteById(id);
    }
}
