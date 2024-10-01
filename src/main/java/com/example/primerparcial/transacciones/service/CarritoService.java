package com.example.primerparcial.transacciones.service;

import com.example.primerparcial.transacciones.models.Carrito;
import com.example.primerparcial.transacciones.repositories.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    public List<Carrito> findAll() {
        return carritoRepository.findAll();
    }

    public Carrito save(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public Carrito findById(Long id) {
        return carritoRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        carritoRepository.deleteById(id);
    }
    public Carrito update(Long id, Carrito carrito) {
        Carrito carritoExistente = findById(id);
        if (carritoExistente == null) {
            return null; // O lanza una excepción según tu preferencia
        }

        // Actualiza los campos del carrito existente
        carritoExistente.setUsuario(carrito.getUsuario());
        carritoExistente.setDisponible(carrito.isDisponible());
        // Actualiza otros campos según sea necesario

        return carritoRepository.save(carritoExistente);
    }

}

