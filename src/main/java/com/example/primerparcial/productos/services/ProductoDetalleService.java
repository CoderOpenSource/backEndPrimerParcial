package com.example.primerparcial.productos.services;

import com.example.primerparcial.config.cloudinary.CloudinaryService;
import com.example.primerparcial.productos.models.ProductoDetalle;
import com.example.primerparcial.productos.repositories.ProductoDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductoDetalleService {

    @Autowired
    private ProductoDetalleRepository productoDetalleRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<ProductoDetalle> findAll() {
        return productoDetalleRepository.findAll();
    }

    public Optional<ProductoDetalle> findById(Long id) {
        return productoDetalleRepository.findById(id);
    }

    public ProductoDetalle save(ProductoDetalle productoDetalle, MultipartFile file) throws IOException {
        // Subir archivo a Cloudinary si está presente
        if (file != null && !file.isEmpty()) {
            Map uploadResult = cloudinaryService.upload(file);
            productoDetalle.setImagen2D((String) uploadResult.get("url"));  // Establecer URL de la imagen
        }

        // Guardar el productoDetalle en la base de datos
        return productoDetalleRepository.save(productoDetalle);
    }

    public ProductoDetalle update(Long id, ProductoDetalle detalles, MultipartFile file) throws IOException {
        Optional<ProductoDetalle> detalleExistente = productoDetalleRepository.findById(id);
        if (detalleExistente.isPresent()) {
            ProductoDetalle detalle = detalleExistente.get();
            detalle.setColor(detalles.getColor());
            detalle.setTamaño(detalles.getTamaño());

            // Subir nueva imagen solo si el archivo es proporcionado
            if (file != null && !file.isEmpty()) {
                Map uploadResult = cloudinaryService.upload(file);
                detalle.setImagen2D((String) uploadResult.get("url"));  // Actualizar URL de la imagen
            }

            return productoDetalleRepository.save(detalle);
        } else {
            throw new IllegalArgumentException("ProductoDetalle no encontrado");
        }
    }

    public void deleteById(Long id) {
        productoDetalleRepository.deleteById(id);
    }
}
