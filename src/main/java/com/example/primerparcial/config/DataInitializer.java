package com.example.primerparcial.config;

import com.example.primerparcial.usuarios.models.Rol;
import com.example.primerparcial.usuarios.models.Usuario;
import com.example.primerparcial.usuarios.services.RolService;
import com.example.primerparcial.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class DataInitializer {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Bean
    public ApplicationRunner init() {
        return args -> {
            // Verificar si el rol "ADMIN" existe, si no lo crea
            Optional<Rol> adminRoleOptional = rolService.obtenerTodosLosRoles()
                    .stream()
                    .filter(rol -> "ADMIN".equals(rol.getNombre()))
                    .findFirst();

            Rol adminRole;
            if (!adminRoleOptional.isPresent()) {
                adminRole = new Rol();
                adminRole.setNombre("ADMIN");
                adminRole = rolService.crearRol(adminRole);
            } else {
                adminRole = adminRoleOptional.get();
            }

            // Verificar si el usuario "admin" existe, si no lo crea
            Optional<Usuario> adminUserOptional = usuarioService.obtenerUsuarioPorEmail("admin@example.com");
            if (!adminUserOptional.isPresent()) {
                Usuario adminUser = new Usuario();
                adminUser.setEmail("admin@example.com");
                adminUser.setNombre("Administrador");
                adminUser.setPassword("admin123"); // Aquí se establece la contraseña en texto plano, será hasheada en el servicio
                adminUser.setRol(adminRole);
                adminUser.setEstado("ACTIVO");

                // Este método se encargará de codificar la contraseña y crear el usuario
                usuarioService.crearUsuario(adminUser);
            }
        };
    }
}
