package com.example.primerparcial.transacciones.controllers;

import com.example.primerparcial.transacciones.models.Factura;
import com.example.primerparcial.transacciones.models.FacturaDTO;
import com.example.primerparcial.transacciones.models.Transaccion;
import com.example.primerparcial.transacciones.service.EmailService;
import com.example.primerparcial.transacciones.service.FacturaService;
import com.example.primerparcial.transacciones.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FacturaService facturaService;

    // Obtener todas las facturas, usando DTO para evitar recursiones
    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAll() {
        List<FacturaDTO> facturas = facturaService.findAll();
        return ResponseEntity.ok(facturas);
    }

    // Obtener una factura específica por ID, usando DTO
    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> getById(@PathVariable Long id) {
        FacturaDTO factura = facturaService.findById(id);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(factura);
    }

    // Crear una nueva factura con un archivo PDF, usando DTO y subiendo el archivo
    @PostMapping
    public ResponseEntity<FacturaDTO> create(
            @RequestParam("transaccionId") Long transaccionId,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Obtener la transacción por transaccionId
        Transaccion transaccion = transaccionService.findById(transaccionId);
        if (transaccion == null) {
            return ResponseEntity.badRequest().body(null); // Devuelve un error si no se encuentra la transacción
        }

        // Imprimir la transacción por consola
        System.out.println("Transacción encontrada: " + transaccion.getUsuario().getEmail());
        String emailCliente = transaccion.getUsuario().getEmail();
        // Subir archivo a Cloudinary y obtener URL del PDF
        String urlPdf = null;
        if (file != null && !file.isEmpty()) {
            urlPdf = facturaService.uploadPdf(file); // Lógica adaptada para PDF
        }

        // Crear la factura
        Factura nuevaFactura = facturaService.createFactura(transaccionId, urlPdf);

        // Enviar correo al cliente con el PDF adjunto
        if (file != null && !file.isEmpty()) {
            File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(tempFile);  // Transferir el archivo MultipartFile a un archivo temporal

            // Enviar el correo al cliente con el archivo PDF adjunto
            String asunto = "Gracias por tu compra!";
            String cuerpo = "Estimado cliente, gracias por tu compra. Te adjuntamos los detalles de tu factura.";
            emailService.enviarCorreoConPDF(emailCliente, asunto, cuerpo, tempFile);

            // Eliminar archivo temporal después de enviarlo
            tempFile.delete();
        }

        // Convertir la factura creada a DTO
        FacturaDTO facturaDTO = new FacturaDTO(
                nuevaFactura.getId(),
                nuevaFactura.getTransaccion().getId(),
                nuevaFactura.getUrlPdf(),
                nuevaFactura.getFecha()
        );
        return ResponseEntity.ok(facturaDTO);
    }

    // Actualizar una factura existente con nuevo archivo PDF, usando DTO
    @PutMapping("/{id}")
    public ResponseEntity<FacturaDTO> update(
            @PathVariable Long id,
            @RequestParam("transaccionId") Long transaccionId,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Subir nuevo archivo a Cloudinary si se proporciona, y obtener URL del PDF
        String urlPdf = null;
        if (file != null && !file.isEmpty()) {
            urlPdf = facturaService.uploadPdf(file); // Lógica adaptada para PDF
        }

        // Actualizar la factura
        FacturaDTO updatedFacturaDTO = facturaService.update(id, transaccionId, urlPdf);
        if (updatedFacturaDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFacturaDTO);
    }

    // Eliminar una factura por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
