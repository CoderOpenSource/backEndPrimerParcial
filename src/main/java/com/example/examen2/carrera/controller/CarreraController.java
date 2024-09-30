package com.example.examen2.carrera.controller;

import com.example.examen2.carrera.model.CarreraDTO;
import com.example.examen2.carrera.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carreras/")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @PostMapping
    public ResponseEntity<CarreraDTO> createCarrera(@RequestBody CarreraDTO carreraDTO) {
        CarreraDTO createdCarrera = carreraService.createCarrera(carreraDTO);
        return ResponseEntity.ok(createdCarrera);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarreraDTO> updateCarrera(@PathVariable Long id, @RequestBody CarreraDTO carreraDTO) {
        CarreraDTO updatedCarrera = carreraService.updateCarrera(id, carreraDTO);
        return ResponseEntity.ok(updatedCarrera);
    }

    @GetMapping
    public ResponseEntity<List<CarreraDTO>> getAllCarreras() {
        List<CarreraDTO> carreras = carreraService.findAll();
        return ResponseEntity.ok(carreras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarreraDTO> getCarreraById(@PathVariable Long id) {
        CarreraDTO carrera = carreraService.findById(id);
        return ResponseEntity.ok(carrera);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrera(@PathVariable Long id) {
        carreraService.deleteCarrera(id);
        return ResponseEntity.noContent().build();
    }
}

