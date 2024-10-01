package com.example.primerparcial.transacciones.service;


import com.example.primerparcial.transacciones.models.Transaccion;
import com.example.primerparcial.transacciones.repositories.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    public List<Transaccion> findAll() {
        return transaccionRepository.findAll();
    }

    public Transaccion save(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    public Transaccion findById(Long id) {
        return transaccionRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        transaccionRepository.deleteById(id);
    }
}

