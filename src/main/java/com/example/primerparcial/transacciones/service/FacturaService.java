package com.example.primerparcial.transacciones.service;

import com.example.primerparcial.config.cloudinary.CloudinaryService;
import com.example.primerparcial.transacciones.models.Factura;
import com.example.primerparcial.transacciones.models.FacturaDTO;
import com.example.primerparcial.transacciones.models.Transaccion;
import com.example.primerparcial.transacciones.repositories.FacturaRepository;
import com.example.primerparcial.transacciones.repositories.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private CloudinaryService cloudinaryService; // Servicio de Cloudinary

    // Método para subir archivo PDF a Cloudinary y obtener URL
    public String uploadPdf(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Map<String, Object> uploadResult = cloudinaryService.upload(file);
            return (String) uploadResult.get("url");  // Devolver URL del PDF
        }
        return null;
    }

    // Método para encontrar todas las facturas, usando DTO
    public List<FacturaDTO> findAll() {
        return facturaRepository.findAll()
                .stream()
                .map(factura -> new FacturaDTO(
                        factura.getId(),
                        factura.getTransaccion().getId(),
                        factura.getUrlPdf(),
                        factura.getFecha()
                ))
                .collect(Collectors.toList());
    }

    // Método para encontrar una factura por ID, usando DTO
    public FacturaDTO findById(Long id) {
        Factura factura = facturaRepository.findById(id).orElse(null);
        if (factura == null) {
            return null;
        }
        return new FacturaDTO(
                factura.getId(),
                factura.getTransaccion().getId(),
                factura.getUrlPdf(),
                factura.getFecha()
        );
    }

    // Crear una nueva factura
    public Factura createFactura(Long transaccionId, String urlPdf) {
        Transaccion transaccion = transaccionRepository.findById(transaccionId)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        Factura factura = new Factura(transaccion, urlPdf);
        factura.setFecha(LocalDateTime.now());  // Establecer fecha actual
        return facturaRepository.save(factura);
    }

    // Actualizar una factura existente
    public FacturaDTO update(Long id, Long transaccionId, String urlPdf) {
        Factura existingFactura = facturaRepository.findById(id).orElse(null);
        if (existingFactura == null) {
            return null;  // O lanzar una excepción personalizada
        }

        // Actualizar la transacción y el URL del PDF si es necesario
        Transaccion transaccion = transaccionRepository.findById(transaccionId)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        existingFactura.setTransaccion(transaccion);

        if (urlPdf != null) {
            existingFactura.setUrlPdf(urlPdf);  // Actualizar URL del PDF solo si se proporciona
        }

        // Guardar factura actualizada
        Factura savedFactura = facturaRepository.save(existingFactura);

        // Convertir a DTO
        return new FacturaDTO(
                savedFactura.getId(),
                savedFactura.getTransaccion().getId(),
                savedFactura.getUrlPdf(),
                savedFactura.getFecha()
        );
    }

    // Eliminar una factura
    public void delete(Long id) {
        facturaRepository.deleteById(id);
    }
    public List<Factura> findByTransaccionId(Long transaccionId) {
        return facturaRepository.findByTransaccionId(transaccionId);
    }

}
