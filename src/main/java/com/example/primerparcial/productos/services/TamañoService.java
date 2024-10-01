package com.example.primerparcial.productos.services;

import com.example.primerparcial.productos.models.Tamaño;
import com.example.primerparcial.productos.repositories.TamañoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TamañoService {

    @Autowired
    private TamañoRepository tamañoRepository;

    public List<Tamaño> findAll() {
        return tamañoRepository.findAll();
    }

    public Optional<Tamaño> findById(Long id) {
        return tamañoRepository.findById(id);
    }

    public Tamaño save(Tamaño tamaño) {
        return tamañoRepository.save(tamaño);
    }

    public void deleteById(Long id) {
        tamañoRepository.deleteById(id);
    }
}

