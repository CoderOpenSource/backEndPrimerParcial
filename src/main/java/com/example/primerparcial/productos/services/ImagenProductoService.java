package com.example.primerparcial.productos.services;

import com.example.primerparcial.productos.models.ImagenProducto;
import com.example.primerparcial.productos.repositories.ImagenProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenProductoService {

    @Autowired
    private ImagenProductoRepository imagenProductoRepository;

    public List<ImagenProducto> findAll() {
        return imagenProductoRepository.findAll();
    }

    public Optional<ImagenProducto> findById(Long id) {
        return imagenProductoRepository.findById(id);
    }

    public ImagenProducto save(ImagenProducto imagenProducto) {
        return imagenProductoRepository.save(imagenProducto);
    }

    public void deleteById(Long id) {
        imagenProductoRepository.deleteById(id);
    }
}

