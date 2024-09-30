package com.example.examen2.auth;

import com.example.examen2.config.jwt.JwtTokenUtil;
import com.example.examen2.common.service.UsuarioDetailsServiceImpl;
import com.example.examen2.common.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsuarioDetailsServiceImpl userDetailsService;

    @PostMapping("/authenticate/")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        logger.info("Attempting to authenticate user: {}", authenticationRequest.getUsername());
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (Exception e) {
            logger.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Obtener el usuario desde el servicio de detalles del usuario
        Usuario usuario = userDetailsService.findByEmail(authenticationRequest.getUsername());
        final String rol = usuario.getRol().getNombre();
        final Long id = usuario.getId();

        logger.info("Authentication successful, token generated for user: {}", authenticationRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, rol, id));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials for user: {}", username);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
