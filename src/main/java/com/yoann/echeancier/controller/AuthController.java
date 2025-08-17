package com.yoann.echeancier.controller;

import com.yoann.echeancier.model.Utilisateur;
import com.yoann.echeancier.payload.ConnexionRequest;
import com.yoann.echeancier.payload.InscriptionRequest;
import com.yoann.echeancier.repository.UtilisateurRepository;
import com.yoann.echeancier.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/inscription")
    public ResponseEntity<?> inscription(@RequestBody InscriptionRequest inscriptionRequest) {
        if (utilisateurRepository.findByEmail(inscriptionRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Cet email est déjà utilisé.");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(inscriptionRequest.getNom());
        utilisateur.setEmail(inscriptionRequest.getEmail());
        utilisateur.setMotDePasse(passwordEncoder.encode(inscriptionRequest.getMotDePasse()));

        utilisateurRepository.save(utilisateur);

        return ResponseEntity.ok("Inscription réussie !");
    }

    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody ConnexionRequest connexionRequest) {
        // Logique de connexion et de génération de JWT
        // (à implémenter en utilisant Spring Security et JwtUtils)
        return ResponseEntity.ok("Connexion réussie !");
    }
}
