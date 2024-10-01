package com.example.primerparcial.transacciones.service;

import com.example.primerparcial.productos.models.ProductoDetalle;
import com.example.primerparcial.productos.repositories.ProductoDetalleRepository;
import com.example.primerparcial.transacciones.models.Carrito;
import com.example.primerparcial.transacciones.models.CarritoProductoDetalle;
import com.example.primerparcial.transacciones.repositories.CarritoProductoDetalleRepository;
import com.example.primerparcial.transacciones.repositories.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoProductoDetalleService {

    @Autowired
    private CarritoProductoDetalleRepository carritoProductoDetalleRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoDetalleRepository productoDetalleRepository;

    public List<CarritoProductoDetalle> findAll() {
        return carritoProductoDetalleRepository.findAll();
    }

    public CarritoProductoDetalle save(CarritoProductoDetalle carritoProductoDetalle) {
        return carritoProductoDetalleRepository.save(carritoProductoDetalle);
    }

    public CarritoProductoDetalle findById(Long id) {
        return carritoProductoDetalleRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        carritoProductoDetalleRepository.deleteById(id);
    }

    // Nuevo método para encontrar Carrito por ID
    public Carrito findCarritoById(Long carritoId) {
        return carritoRepository.findById(carritoId).orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
    }

    // Nuevo método para encontrar ProductoDetalle por ID
    public ProductoDetalle findProductoDetalleById(Long productoDetalleId) {
        return productoDetalleRepository.findById(productoDetalleId).orElseThrow(() -> new IllegalArgumentException("Producto Detalle no encontrado"));
    }
}
