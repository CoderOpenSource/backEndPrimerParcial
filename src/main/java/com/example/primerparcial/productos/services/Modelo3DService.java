package com.example.primerparcial.productos.services;

import com.example.primerparcial.productos.models.Modelo3D;
import com.example.primerparcial.productos.repositories.Modelo3DRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Modelo3DService {

    @Autowired
    private Modelo3DRepository modelo3DRepository;

    public List<Modelo3D> findAll() {
        return modelo3DRepository.findAll();
    }

    public Optional<Modelo3D> findById(Long id) {
        return modelo3DRepository.findById(id);
    }

    public Modelo3D save(Modelo3D modelo3D) {
        return modelo3DRepository.save(modelo3D);
    }

    public void deleteById(Long id) {
        modelo3DRepository.deleteById(id);
    }
}

