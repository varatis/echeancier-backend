package com.yoann.echeancier.controller;

import com.yoann.echeancier.config.JwtTokenProvider;
import com.yoann.echeancier.model.Utilisateur;
import com.yoann.echeancier.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:4200"}, allowCredentials = "true") // ✅ Correct
public class UtilisateurController {


    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/inscription")
    public ResponseEntity<?> inscrire(@Valid @RequestBody Map<String, String> inscription) {
        try {
            String nomUtilisateur = inscription.get("nomUtilisateur");
            String email = inscription.get("email");
            String motDePasse = inscription.get("motDePasse");
            String nom = inscription.get("nom");
            String prenom = inscription.get("prenom");

            Utilisateur utilisateur = utilisateurService.creerUtilisateur(nomUtilisateur, email, motDePasse, nom, prenom);

            // Réponse sans le mot de passe
            Map<String, Object> reponse = Map.of(
                    "id", utilisateur.getId(),
                    "nomUtilisateur", utilisateur.getNomUtilisateur(),
                    "email", utilisateur.getEmail(),
                    "nom", utilisateur.getNom(),
                    "prenom", utilisateur.getPrenom()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Inscription réussie", "utilisateur", reponse));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erreur", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de l'inscription"));
        }
    }

    @PostMapping("/connexion")
    public ResponseEntity<?> connecter(@RequestBody Map<String, String> creds) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(creds.get("email"), creds.get("motDePasse"))
            );
            System.out.println("Authentification réussie !");
            // La connexion est réussie. Générez et retournez le token.
            // Utilisez la classe que nous avons créée précédemment pour générer le token JWT.
            String token = jwtTokenProvider.generateToken(authentication.getName());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Connexion réussie");
            response.put("token", token);
            // Vous pouvez aussi retourner les infos de l'utilisateur si vous le souhaitez

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Erreur d'authentification : " + e.getMessage()));

            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Email ou mot de passe incorrect."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenirUtilisateur(@PathVariable Long id) {
        try {
            Utilisateur utilisateur = utilisateurService.obtenirUtilisateurParId(id);

            // Réponse sans le mot de passe
            Map<String, Object> reponse = Map.of(
                    "id", utilisateur.getId(),
                    "nomUtilisateur", utilisateur.getNomUtilisateur(),
                    "email", utilisateur.getEmail(),
                    "nom", utilisateur.getNom(),
                    "prenom", utilisateur.getPrenom()
            );

            return ResponseEntity.ok(reponse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de la récupération de l'utilisateur"));
        }
    }
}
