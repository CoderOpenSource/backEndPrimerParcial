package com.example.examen2.asistencia.service;

import com.example.examen2.asistencia.model.Licencia;
import com.example.examen2.asistencia.model.LicenciaDTO;
import com.example.examen2.asistencia.repository.LicenciaRepository;
import com.example.examen2.config.cloudinary.CloudinaryService;
import com.example.examen2.docente.model.Docente;
import com.example.examen2.docente.repository.DocenteRepository;
import com.example.examen2.programacionacademica.model.ProgramacionAcademica;
import com.example.examen2.programacionacademica.repository.ProgramacionAcademicaRepository;
import com.example.examen2.reporte.model.Reporte;
import com.example.examen2.reporte.repository.ReporteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LicenciaService {

    @Autowired
    private LicenciaRepository licenciaRepository;

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private ProgramacionAcademicaRepository programacionAcademicaRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public List<LicenciaDTO> findAll() {
        return licenciaRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public LicenciaDTO findById(Long id) {
        Licencia licencia = licenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Licencia not found with id: " + id));
        return mapEntityToDto(licencia);
    }

    public LicenciaDTO createOrUpdateLicencia(LicenciaDTO licenciaDTO, MultipartFile file) throws IOException {
        ProgramacionAcademica programacionAcademica = programacionAcademicaRepository.findById(licenciaDTO.getProgramacionAcademicaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid programacionAcademicaId: " + licenciaDTO.getProgramacionAcademicaId()));

        Set<Docente> docentes = programacionAcademica.getDocentes();
        Docente docente = docenteRepository.findById(licenciaDTO.getDocenteId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid docenteId: " + licenciaDTO.getDocenteId()));

        // Validación para asegurarse de que el docente esté en la programación académica
        if (!docentes.contains(docente)) {
            throw new IllegalArgumentException("El docente no está asociado con la programación académica especificada.");
        }
        System.out.println("Fecha recibida" + licenciaDTO.getFecha());
        // Verificar si ya existe una licencia para el mismo docente y día
        LocalDate fecha = licenciaDTO.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("Fecha obtenida: " + fecha);
        List<Licencia> existingLicencias = licenciaRepository.findByDocenteAndDate(docente.getId(), fecha);

        /*if (!existingLicencias.isEmpty()) {
            throw new IllegalArgumentException("Ya existe una licencia para este docente en el mismo día.");
        }
*/
        // Verificar si la fecha de la licencia coincide con algún día de la programación académica

        boolean esDiaValido = programacionAcademica.getSesiones().stream()
                .anyMatch(sesion -> {
                    System.out.println("Comparando día de la sesión: " + sesion.getDiaSemana() + " con día de la fecha: " + fecha.getDayOfWeek());
                    return sesion.getDiaSemana() == fecha.getDayOfWeek();
                });



        Licencia licencia;
        if (licenciaDTO.getId() != null) {
            licencia = licenciaRepository.findById(licenciaDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Licencia not found with id: " + licenciaDTO.getId()));
            updateEntityFromDto(licencia, licenciaDTO);
        } else {
            licencia = mapDtoToEntity(licenciaDTO);
        }

        // Subir archivo a Cloudinary
        if (file != null && !file.isEmpty()) {
            Map uploadResult = cloudinaryService.upload(file);
            licencia.setFotoLicencia((String) uploadResult.get("url"));
        }

        licencia = licenciaRepository.save(licencia);
        return mapEntityToDto(licencia);
    }

    @Transactional
    public void deleteLicencia(Long id) {
        Licencia licencia = licenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Licencia not found with id: " + id));

        for (Reporte reporte : licencia.getReportes()) {
            reporte.getLicencias().remove(licencia);
            reporteRepository.save(reporte);
        }

        licenciaRepository.delete(licencia);
    }

    private Licencia mapDtoToEntity(LicenciaDTO dto) {
        Licencia licencia = new Licencia();
        licencia.setDocente(docenteRepository.findById(dto.getDocenteId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid docenteId: " + dto.getDocenteId())));
        licencia.setProgramacionAcademica(programacionAcademicaRepository.findById(dto.getProgramacionAcademicaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid programacionAcademicaId: " + dto.getProgramacionAcademicaId())));
        licencia.setFecha(dto.getFecha());
        licencia.setMotivo(dto.getMotivo());
        licencia.setFotoLicencia(dto.getFotoLicencia());
        return licencia;
    }

    private void updateEntityFromDto(Licencia licencia, LicenciaDTO dto) {
        licencia.setFecha(dto.getFecha());
        licencia.setMotivo(dto.getMotivo());
        licencia.setProgramacionAcademica(programacionAcademicaRepository.findById(dto.getProgramacionAcademicaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid programacionAcademicaId: " + dto.getProgramacionAcademicaId())));
        licencia.setFotoLicencia(dto.getFotoLicencia());
    }

    private LicenciaDTO mapEntityToDto(Licencia licencia) {
        LicenciaDTO dto = new LicenciaDTO();
        dto.setId(licencia.getId());
        dto.setDocenteId(licencia.getDocente().getId());
        dto.setProgramacionAcademicaId(licencia.getProgramacionAcademica().getId());
        dto.setFecha(licencia.getFecha());
        dto.setMotivo(licencia.getMotivo());
        dto.setFotoLicencia(licencia.getFotoLicencia());
        return dto;
    }
}