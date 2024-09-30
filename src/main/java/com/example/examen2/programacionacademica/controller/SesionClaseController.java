package com.example.examen2.programacionacademica.controller;

import com.example.examen2.programacionacademica.model.SesionClase;
import com.example.examen2.programacionacademica.model.SesionClaseDTO;
import com.example.examen2.programacionacademica.service.SesionClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sesionesclase/")
public class SesionClaseController {

    @Autowired
    private SesionClaseService sesionClaseService;

    @GetMapping("/")
    public List<SesionClaseDTO> getAllSesionesClase() {
        return sesionClaseService.findAll();
    }

    @PostMapping("/")
    public SesionClaseDTO createSesionClase(@RequestBody SesionClaseDTO sesionClaseDTO) {
        return sesionClaseService.createSesionClase(sesionClaseDTO);
    }

    @PutMapping("/{id}")
    public SesionClaseDTO updateSesionClase(@PathVariable Long id, @RequestBody SesionClaseDTO sesionClaseDTO) {
        return sesionClaseService.updateSesionClase(id, sesionClaseDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSesionClase(@PathVariable Long id) {
        sesionClaseService.deleteSesionClase(id);
    }
}
