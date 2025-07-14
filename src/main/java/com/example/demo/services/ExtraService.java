package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Extra;
import com.example.demo.repositories.ExtraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExtraService {
    @Autowired
    private final ExtraRepository repository;

    public List<Extra> sel() {
        return repository.findAll();
    }

    public Extra selectOne (Long id) {
        return repository.findById(id).orElse(null);
    }

    public Extra insertUpdate (Extra extra) {
        return repository.save(extra);
    }

    public void delete (Long id) {
        repository.deleteById(id);
    }
}
