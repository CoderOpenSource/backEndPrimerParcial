package com.example.examen2.reporte.controller;

import com.example.examen2.asistencia.model.Asistencia;
import com.example.examen2.asistencia.model.Licencia;
import com.example.examen2.asistencia.service.AsistenciaService;
import com.example.examen2.asistencia.service.LicenciaService;
import com.example.examen2.reporte.model.ReporteDTO;
import com.example.examen2.reporte.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reportes/")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private LicenciaService licenciaService;

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> getAllReportes() {
        List<ReporteDTO> reportes = reporteService.findAll();
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteDTO> getReporteById(@PathVariable Long id) {
        ReporteDTO reporte = reporteService.findById(id);
        return ResponseEntity.ok(reporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteDTO> updateReporte(@PathVariable Long id, @RequestBody ReporteDTO reporteDTO) {
        ReporteDTO updatedReporte = reporteService.updateReporte(id, reporteDTO);
        return ResponseEntity.ok(updatedReporte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        reporteService.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }



    private static final String REPORTS_DIR = "src/main/resources/static/reports/";

    

    @GetMapping("/asistencias/pdf")
    public ResponseEntity<byte[]> getReporteAsistenciasPdf(
            @RequestParam(required = false) List<Long> asistenciaIds,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) String materia,
            @RequestParam(required = false) String docente) throws IOException {
        System.out.println("getReporteAsistenciasPdf called");

        List<Asistencia> asistencias;
        if (asistenciaIds == null || asistenciaIds.isEmpty()) {
            asistencias = reporteService.findAsistenciasByFilters(fechaInicio, fechaFin, materia, docente);
        } else {
            asistencias = asistenciaService.findByEstadoPresente().stream()
                    .filter(asistencia -> asistenciaIds.contains(asistencia.getId()))
                    .collect(Collectors.toList());
        }

        ReporteDTO reporteDTO = reporteService.createReportePdfFromAsistencias(asistencias, 1L);
        ByteArrayInputStream bis = reporteService.generateReporteAsistenciasPdf(asistencias, reporteDTO.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_" + reporteDTO.getId() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

    @GetMapping("/asistencias/excel")
    public ResponseEntity<byte[]> getReporteAsistenciasExcel(
            @RequestParam(required = false) List<Long> asistenciaIds,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) String materia,
            @RequestParam(required = false) String docente) throws IOException {
        List<Asistencia> asistencias;
        if (asistenciaIds == null || asistenciaIds.isEmpty()) {
            asistencias = reporteService.findAsistenciasByFilters(fechaInicio, fechaFin, materia, docente);
        } else {
            asistencias = asistenciaService.findByEstadoPresente().stream()
                    .filter(asistencia -> asistenciaIds.contains(asistencia.getId()))
                    .collect(Collectors.toList());
        }

        ReporteDTO reporteDTO = reporteService.createReporteExcelFromAsistencias(asistencias, 1L);
        ByteArrayInputStream bis = reporteService.generateReporteAsistenciasExcel(asistencias, reporteDTO.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporte_" + reporteDTO.getId() + ".xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(bis.readAllBytes());
    }

    @GetMapping("/atrasos/pdf")
    public ResponseEntity<byte[]> getReporteAtrasosPdf(
            @RequestParam(required = false) List<Long> asistenciaIds,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) String materia,
            @RequestParam(required = false) String docente) throws IOException {
        List<Asistencia> asistencias;
        if (asistenciaIds == null || asistenciaIds.isEmpty()) {
            asistencias = reporteService.findAtrasosByFilters(fechaInicio, fechaFin, materia, docente);
        } else {
            asistencias = asistenciaService.findByEstadoRetraso().stream()
                    .filter(asistencia -> asistenciaIds.contains(asistencia.getId()))
                    .collect(Collectors.toList());
        }

        ReporteDTO reporteDTO = reporteService.createReportePdfFromAsistencias(asistencias, 2L);
        ByteArrayInputStream bis = reporteService.generateReporteAsistenciasPdf(asistencias, reporteDTO.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_" + reporteDTO.getId() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

    @GetMapping("/atrasos/excel")
    public ResponseEntity<byte[]> getReporteAtrasosExcel(
            @RequestParam(required = false) List<Long> asistenciaIds,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) String materia,
            @RequestParam(required = false) String docente) throws IOException {
        List<Asistencia> asistencias;
        if (asistenciaIds == null || asistenciaIds.isEmpty()) {
            asistencias = reporteService.findAtrasosByFilters(fechaInicio, fechaFin, materia, docente);
        } else {
            asistencias = asistenciaService.findByEstadoRetraso().stream()
                    .filter(asistencia -> asistenciaIds.contains(asistencia.getId()))
                    .collect(Collectors.toList());
        }

        ReporteDTO reporteDTO = reporteService.createReporteExcelFromAsistencias(asistencias, 2L);
        ByteArrayInputStream bis = reporteService.generateReporteAsistenciasExcel(asistencias, reporteDTO.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporte_" + reporteDTO.getId() + ".xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(bis.readAllBytes());
    }

    @GetMapping("/faltas/pdf")
    public ResponseEntity<byte[]> getReporteFaltasPdf(
            @RequestParam(required = false) List<Long> asistenciaIds,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) String materia,
            @RequestParam(required = false) String docente) throws IOException {
        List<Asistencia> asistencias;
        if (asistenciaIds == null || asistenciaIds.isEmpty()) {
            asistencias = reporteService.findFaltasByFilters(fechaInicio, fechaFin, materia, docente);
        } else {
            asistencias = asistenciaService.findByEstadoAusente().stream()
                    .filter(asistencia -> asistenciaIds.contains(asistencia.getId()))
                    .collect(Collectors.toList());
        }

        ReporteDTO reporteDTO = reporteService.createReportePdfFromAsistencias(asistencias, 3L);
        ByteArrayInputStream bis = reporteService.generateReporteAsistenciasPdf(asistencias, reporteDTO.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_" + reporteDTO.getId() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

    @GetMapping("/faltas/excel")
    public ResponseEntity<byte[]> getReporteFaltasExcel(
            @RequestParam(required = false) List<Long> asistenciaIds,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) String materia,
            @RequestParam(required = false) String docente) throws IOException {
        List<Asistencia> asistencias;
        if (asistenciaIds == null || asistenciaIds.isEmpty()) {
            asistencias = reporteService.findFaltasByFilters(fechaInicio, fechaFin, materia, docente);
        } else {
            asistencias = asistenciaService.findByEstadoAusente().stream()
                    .filter(asistencia -> asistenciaIds.contains(asistencia.getId()))
                    .collect(Collectors.toList());
        }

        ReporteDTO reporteDTO = reporteService.createReporteExcelFromAsistencias(asistencias, 3L);
        ByteArrayInputStream bis = reporteService.generateReporteAsistenciasExcel(asistencias, reporteDTO.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporte_" + reporteDTO.getId() + ".xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(bis.readAllBytes());
    }


    @GetMapping("/licencias/pdf")
    public ResponseEntity<byte[]> getReporteLicenciasPdf(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) String materia,
            @RequestParam(required = false) String docente) throws IOException {
        List<Licencia> licencias;
        licencias = reporteService.findLicenciasByFilters(fechaInicio, fechaFin, materia, docente);


        ReporteDTO reporteDTO = reporteService.createReportePdfFromLicencias(licencias, 4L);
        ByteArrayInputStream bis = reporteService.generateReporteLicenciasPdf(licencias, reporteDTO.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_" + reporteDTO.getId() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

    @GetMapping("/licencias/excel")
    public ResponseEntity<byte[]> getReporteLicenciasExcel(

            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) String materia,
            @RequestParam(required = false) String docente) throws IOException {
        List<Licencia> licencias;
            licencias = reporteService.findLicenciasByFilters(fechaInicio, fechaFin, materia, docente);


        ReporteDTO reporteDTO = reporteService.createReporteExcelFromLicencias(licencias, 4L);
        ByteArrayInputStream bis = reporteService.generateReporteLicenciasExcel(licencias, reporteDTO.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporte_" + reporteDTO.getId() + ".xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(bis.readAllBytes());
    }

    @GetMapping("/static/reports/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = Paths.get("src/main/resources/static/reports").resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
