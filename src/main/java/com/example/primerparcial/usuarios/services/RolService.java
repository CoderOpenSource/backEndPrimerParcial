package com.example.primerparcial.usuarios.services;

import com.example.primerparcial.usuarios.models.Rol;
import com.example.primerparcial.usuarios.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    public Rol actualizarRol(Long id, Rol rolActualizado) {
        Rol rol = rolRepository.findById(id).orElseThrow();
        rol.setNombre(rolActualizado.getNombre());
        return rolRepository.save(rol);
    }

    public void eliminarRol(Long id) {
        rolRepository.deleteById(id);
    }
}

