package com.yoann.echeancier.service;

import com.yoann.echeancier.dto.CreerDepenseDto;
import com.yoann.echeancier.dto.DepenseDto;
import com.yoann.echeancier.dto.SoldeDto;
import com.yoann.echeancier.model.Depense;
import com.yoann.echeancier.model.Utilisateur;
import com.yoann.echeancier.repository.DepenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepenseService {

    @Autowired
    private DepenseRepository depenseRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    public Depense creerDepense(CreerDepenseDto creerDepenseDto, Long utilisateurId) {
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurParId(utilisateurId);

        Depense depense = new Depense();
        depense.setDescription(creerDepenseDto.getDescription());
        depense.setMontant(creerDepenseDto.getMontant());
        depense.setDateDepense(creerDepenseDto.getDateDepense() != null ? creerDepenseDto.getDateDepense() : LocalDate.now());

        // ⭐ AJOUT MANQUANT : définir la catégorie
        depense.setCategorie(creerDepenseDto.getCategorie());

        depense.setUtilisateur(utilisateur);

        return depenseRepository.save(depense);
    }

    public List<DepenseDto> obtenirDepensesParUtilisateur(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurParId(utilisateurId);
        List<Depense> depenses = depenseRepository.findByUtilisateurOrderByDateDepenseDesc(utilisateur);
        return depenses.stream()
                .map(this::convertirEnDto)
                .collect(Collectors.toList());
    }

    public Depense modifierDepense(Long depenseId, CreerDepenseDto modifierDepenseDto, Long utilisateurId) {
        Depense depense = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new RuntimeException("Dépense non trouvée"));

        if (!depense.getUtilisateur().getId().equals(utilisateurId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette dépense");
        }

        depense.setDescription(modifierDepenseDto.getDescription());
        depense.setMontant(modifierDepenseDto.getMontant());
        depense.setDateDepense(modifierDepenseDto.getDateDepense() != null ? modifierDepenseDto.getDateDepense() : depense.getDateDepense());

        return depenseRepository.save(depense);
    }

    public void supprimerDepense(Long depenseId, Long utilisateurId) {
        Depense depense = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new RuntimeException("Dépense non trouvée"));

        if (!depense.getUtilisateur().getId().equals(utilisateurId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette dépense");
        }

        depenseRepository.delete(depense);
    }

    public SoldeDto obtenirSolde(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurParId(utilisateurId);

        BigDecimal totalDepenses = depenseRepository.getTotalDepensesByUtilisateur(utilisateur);
        if (totalDepenses == null) totalDepenses = BigDecimal.ZERO;

        Long nombreDepenses = depenseRepository.countDepensesByUtilisateur(utilisateur);
        if (nombreDepenses == null) nombreDepenses = 0L;

        BigDecimal depenseMoyenne = BigDecimal.ZERO;
        if (nombreDepenses > 0) {
            depenseMoyenne = totalDepenses.divide(BigDecimal.valueOf(nombreDepenses), 2, RoundingMode.HALF_UP);
        }

        List<Object[]> depensesParCategorieRaw = depenseRepository.getDepensesParCategorie(utilisateur);
        Map<String, BigDecimal> depensesParCategorie = new HashMap<>();

        for (Object[] row : depensesParCategorieRaw) {
            String categorie = (String) row[0];
            BigDecimal montant = (BigDecimal) row[1];
            depensesParCategorie.put(categorie, montant);
        }

        SoldeDto solde = new SoldeDto(totalDepenses, nombreDepenses, depenseMoyenne);
        solde.setDepensesParCategorie(depensesParCategorie);

        return solde;
    }

    private DepenseDto convertirEnDto(Depense depense) {
        DepenseDto dto = new DepenseDto();
        dto.setId(depense.getId());
        dto.setDescription(depense.getDescription());
        dto.setMontant(depense.getMontant());
        dto.setCategorie(depense.getCategorie());
        dto.setDateDepense(depense.getDateDepense());
        return dto;
    }
}
