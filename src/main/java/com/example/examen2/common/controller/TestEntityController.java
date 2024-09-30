package com.example.examen2.common.controller;

import com.example.examen2.common.model.TestEntity;
import com.example.examen2.common.repository.TestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestEntityController {
    @Autowired
    private TestEntityRepository testEntityRepository;

    @GetMapping
    public List<TestEntity> getAll() {
        return testEntityRepository.findAll();
    }

    @PostMapping
    public TestEntity create(@RequestBody TestEntity testEntity) {
        return testEntityRepository.save(testEntity);
    }
}
