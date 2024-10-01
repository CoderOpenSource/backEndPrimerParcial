package com.example.primerparcial.valoraciones.services;

import com.example.primerparcial.productos.models.Producto;
import com.example.primerparcial.usuarios.models.Usuario;
import com.example.primerparcial.valoraciones.models.Valoracion;
import com.example.primerparcial.valoraciones.repositories.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRepository;

    public List<Valoracion> findAllValoraciones() {
        return valoracionRepository.findAll();
    }

    public List<Valoracion> findByUsuario(Usuario usuario) {
        return valoracionRepository.findByUsuario(usuario);
    }

    public List<Valoracion> findByProducto(Producto producto) {
        return valoracionRepository.findByProducto(producto);
    }

    public Optional<Valoracion> findByUsuarioAndProducto(Usuario usuario, Producto producto) {
        return valoracionRepository.findByUsuarioAndProducto(usuario, producto);
    }

    public Valoracion saveValoracion(Valoracion valoracion) {
        return valoracionRepository.save(valoracion);
    }

    public Optional<Valoracion> findById(Long id) {
        return valoracionRepository.findById(id);
    }

    public void deleteValoracion(Long id) {
        valoracionRepository.deleteById(id);
    }
}
