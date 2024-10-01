package com.example.primerparcial.Reporte.controllers;

import com.example.primerparcial.Reporte.models.Reporte;
import com.example.primerparcial.Reporte.services.ReporteVentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reportes/ventas")
public class ReporteController {

    @Autowired
    private ReporteVentasService reporteVentasService;

    /**
     * Obtener todos los reportes generados
     */
    @GetMapping("/all")
    public ResponseEntity<List<Reporte>> obtenerTodosLosReportes() {
        List<Reporte> reportes = reporteVentasService.obtenerTodosLosReportes();
        return ResponseEntity.ok(reportes);
    }

    /**
     * Generar reporte en formato Excel y devolver la URL del archivo
     */
    @GetMapping("/excel")
    public ResponseEntity<Reporte> generarReporteVentasExcel(
            @RequestParam(value = "fechaInicio", required = false) String fechaInicio,
            @RequestParam(value = "fechaFin", required = false) String fechaFin,
            @RequestParam(value = "idUsuario", required = false) Long idUsuario,
            @RequestParam(value = "idProducto", required = false) Long idProducto,
            @RequestParam(value = "idTipoPago", required = false) Long idTipoPago) throws IOException {

        // Generar y guardar el reporte Excel
        Reporte reporte = reporteVentasService.generarYGuardarReporteExcel(fechaInicio, fechaFin, idUsuario, idProducto, idTipoPago);
        return ResponseEntity.ok(reporte);
    }

    /**
     * Generar reporte en formato PDF y devolver la URL del archivo
     */
    @GetMapping("/pdf")
    public ResponseEntity<Reporte> generarReporteVentasPdf(
            @RequestParam(value = "fechaInicio", required = false) String fechaInicio,
            @RequestParam(value = "fechaFin", required = false) String fechaFin,
            @RequestParam(value = "idUsuario", required = false) Long idUsuario,
            @RequestParam(value = "idProducto", required = false) Long idProducto,
            @RequestParam(value = "idTipoPago", required = false) Long idTipoPago) throws IOException {

        // Generar y guardar el reporte PDF
        Reporte reporte = reporteVentasService.generarYGuardarReportePdf(fechaInicio, fechaFin, idUsuario, idProducto, idTipoPago);
        return ResponseEntity.ok(reporte);
    }

    /**
     * Eliminar un reporte por su ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        reporteVentasService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
