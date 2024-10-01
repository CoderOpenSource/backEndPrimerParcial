package com.example.primerparcial.productos.services;

import com.example.primerparcial.productos.models.Subcategoria;
import com.example.primerparcial.productos.repositories.SubcategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubcategoriaService {

    @Autowired
    private SubcategoriaRepository subcategoriaRepository;

    public List<Subcategoria> findAll() {
        return subcategoriaRepository.findAll();
    }

    public Optional<Subcategoria> findById(Long id) {
        return subcategoriaRepository.findById(id);
    }

    public Subcategoria save(Subcategoria subcategoria) {
        return subcategoriaRepository.save(subcategoria);
    }

    public void deleteById(Long id) {
        subcategoriaRepository.deleteById(id);
    }
}

