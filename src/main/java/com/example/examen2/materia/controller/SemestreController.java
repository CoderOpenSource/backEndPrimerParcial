package com.example.examen2.materia.controller;


import com.example.examen2.materia.model.SemestreDTO;
import com.example.examen2.materia.service.SemestreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semestres/")
public class SemestreController {

    @Autowired
    private SemestreService semestreService;

    @GetMapping
    public List<SemestreDTO> findAll() {
        return semestreService.findAll();
    }

    @GetMapping("/{id}")
    public SemestreDTO findById(@PathVariable Long id) {
        return semestreService.findById(id);
    }

    @PostMapping
    public SemestreDTO create(@RequestBody SemestreDTO semestreDTO) {
        return semestreService.createSemestre(semestreDTO);
    }

    @PutMapping("/{id}")
    public SemestreDTO update(@PathVariable Long id, @RequestBody SemestreDTO semestreDTO) {
        return semestreService.updateSemestre(id, semestreDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        semestreService.deleteSemestre(id);
    }
}
