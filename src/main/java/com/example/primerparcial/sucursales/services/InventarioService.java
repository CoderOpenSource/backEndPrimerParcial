package com.example.primerparcial.sucursales.services;

import com.example.primerparcial.productos.models.ProductoDetalle;
import com.example.primerparcial.sucursales.models.Inventario;
import com.example.primerparcial.sucursales.models.Sucursal;
import com.example.primerparcial.sucursales.repositories.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> findAllInventarios() {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> findById(Long id) {
        return inventarioRepository.findById(id);
    }

    public List<Inventario> findBySucursal(Sucursal sucursal) {
        return inventarioRepository.findBySucursal(sucursal);
    }

    public Inventario saveInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public void deleteInventario(Long id) {
        inventarioRepository.deleteById(id);
    }
    public Optional<Inventario> findByProductoDetalle(ProductoDetalle productoDetalle) {
        return inventarioRepository.findByProductodetalle(productoDetalle);
    }

}
