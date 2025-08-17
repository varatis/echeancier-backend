package com.yoann.echeancier.service;

import com.yoann.echeancier.model.Utilisateur;
import com.yoann.echeancier.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private final BCryptPasswordEncoder encodeurMotDePasse = new BCryptPasswordEncoder();

    public Utilisateur creerUtilisateur(String nomUtilisateur, String email, String motDePasse) {
        // Vérifier si l'email existe déjà
        if (utilisateurRepository.existsByEmail(email)) {
            throw new RuntimeException("Un compte avec cet email existe déjà");
        }

        // Vérifier si le nom d'utilisateur existe déjà
        if (utilisateurRepository.existsByNomUtilisateur(nomUtilisateur)) {
            throw new RuntimeException("Ce nom d'utilisateur est déjà pris");
        }

        // Créer nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(nomUtilisateur);
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(encodeurMotDePasse.encode(motDePasse));

        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur connecterUtilisateur(String email, String motDePasse) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);

        if (!utilisateurOpt.isPresent()) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        Utilisateur utilisateur = utilisateurOpt.get();

        if (!encodeurMotDePasse.matches(motDePasse, utilisateur.getMotDePasse())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        return utilisateur;
    }

    public Utilisateur obtenirUtilisateurParId(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public Utilisateur obtenirUtilisateurParEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}
