package com.example.primerparcial.transacciones.service;

import com.example.primerparcial.transacciones.models.TipoPago;
import com.example.primerparcial.transacciones.repositories.TipoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPagoService {

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    public List<TipoPago> findAll() {
        return tipoPagoRepository.findAll();
    }

    public TipoPago save(TipoPago tipoPago) {
        return tipoPagoRepository.save(tipoPago);
    }

    public TipoPago findById(Long id) {
        return tipoPagoRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        tipoPagoRepository.deleteById(id);
    }
}

