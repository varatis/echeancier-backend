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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:4200"}, allowCredentials = "true")
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

            Utilisateur utilisateur = utilisateurService.creerUtilisateur(nomUtilisateur, email, motDePasse);

            // Réponse sans le mot de passe
            Map<String, Object> reponse = Map.of(
                    "id", utilisateur.getId(),
                    "nomUtilisateur", utilisateur.getNomUtilisateur(),
                    "email", utilisateur.getEmail()
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

            // Générer et retourner le token.
            String token = jwtTokenProvider.generateToken(authentication.getName());

            // Récupérer les informations de l'utilisateur en utilisant la bonne méthode du service.
            // La méthode obtenirUtilisateurParEmail lève une exception si l'utilisateur n'est pas trouvé.
            Utilisateur utilisateur = utilisateurService.obtenirUtilisateurParEmail(creds.get("email"));

            // Créer l'objet utilisateur à retourner (sans le mot de passe)
            Map<String, Object> userResponse = new HashMap<>();
            userResponse.put("id", utilisateur.getId());
            userResponse.put("nomUtilisateur", utilisateur.getNomUtilisateur());
            userResponse.put("email", utilisateur.getEmail());

            // Construire la réponse finale
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Connexion réussie");
            response.put("token", token);
            response.put("user", userResponse); // Ajout de l'objet utilisateur

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Email ou mot de passe incorrect."));
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
                    "email", utilisateur.getEmail()
            );

            return ResponseEntity.ok(reponse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de la récupération de l'utilisateur"));
        }
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> obtenirTousLesUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.obtenirTousLesUtilisateurs();

        // Convertir la liste d'utilisateurs en une liste de Maps pour cacher les mots de passe
        List<Map<String, Object>> reponse = utilisateurs.stream()
                .map(utilisateur -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("id", utilisateur.getId());
                    userMap.put("nomUtilisateur", utilisateur.getNomUtilisateur());
                    userMap.put("email", utilisateur.getEmail());
                    return userMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(reponse);
    }

}
