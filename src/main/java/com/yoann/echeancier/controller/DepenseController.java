package com.yoann.echeancier.controller;
import com.yoann.echeancier.dto.CreerDepenseDto;
import com.yoann.echeancier.dto.DepenseDto;
import com.yoann.echeancier.dto.SoldeDto;
import com.yoann.echeancier.model.Depense;
import com.yoann.echeancier.service.DepenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/depenses")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:4200"}, allowCredentials = "true")
public class DepenseController {

    @Autowired
    private DepenseService depenseService;

    @PostMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<?> creerDepense(
            @PathVariable Long utilisateurId,
            @Valid @RequestBody CreerDepenseDto creerDepenseDto) {
        try {
            Depense nouvelleDepense = depenseService.creerDepense(creerDepenseDto, utilisateurId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Dépense créée avec succès", "depense", nouvelleDepense));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erreur", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de la création de la dépense"));
        }
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<?> obtenirDepensesParUtilisateur(@PathVariable Long utilisateurId) {
        try {
            List<DepenseDto> depenses = depenseService.obtenirDepensesParUtilisateur(utilisateurId);
            return ResponseEntity.ok(depenses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erreur", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de la récupération des dépenses"));
        }
    }

    @PutMapping("/{depenseId}/utilisateur/{utilisateurId}")
    public ResponseEntity<?> modifierDepense(
            @PathVariable Long depenseId,
            @PathVariable Long utilisateurId,
            @Valid @RequestBody CreerDepenseDto modifierDepenseDto) {
        try {
            Depense depenseModifiee = depenseService.modifierDepense(depenseId, modifierDepenseDto, utilisateurId);
            return ResponseEntity.ok(Map.of("message", "Dépense modifiée avec succès", "depense", depenseModifiee));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erreur", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de la modification de la dépense"));
        }
    }

    @DeleteMapping("/{depenseId}/utilisateur/{utilisateurId}")
    public ResponseEntity<?> supprimerDepense(
            @PathVariable Long depenseId,
            @PathVariable Long utilisateurId) {
        try {
            depenseService.supprimerDepense(depenseId, utilisateurId);
            return ResponseEntity.ok(Map.of("message", "Dépense supprimée avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erreur", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de la suppression de la dépense"));
        }
    }

    @GetMapping("/utilisateur/{utilisateurId}/solde")
    public ResponseEntity<?> obtenirSolde(@PathVariable Long utilisateurId) {
        try {
            SoldeDto solde = depenseService.obtenirSolde(utilisateurId);
            return ResponseEntity.ok(solde);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erreur", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de la récupération du solde"));
        }
    }
}
