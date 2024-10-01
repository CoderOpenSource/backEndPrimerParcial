package com.example.primerparcial.usuarios.repositories;

import com.example.primerparcial.usuarios.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findUsuarioById(Long id);
}
