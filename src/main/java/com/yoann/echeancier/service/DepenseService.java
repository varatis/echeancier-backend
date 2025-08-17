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
        depense.setCategorie(creerDepenseDto.getCategorie());
        depense.setDateDepense(creerDepenseDto.getDateDepense());
        depense.setCommentaires(creerDepenseDto.getCommentaires());
        depense.setUtilisateur(utilisateur);
        depense.setDateCreation(LocalDateTime.now());

        return depenseRepository.save(depense);
    }

    public List<Depense> obtenirDepensesUtilisateur(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurParId(utilisateurId);
        return depenseRepository.findByUtilisateurOrderByDateDepenseDesc(utilisateur);
    }

    public List<DepenseDto> obtenirDepensesDtoUtilisateur(Long utilisateurId) {
        List<Depense> depenses = obtenirDepensesUtilisateur(utilisateurId);
        return depenses.stream()
                .map(this::convertirEnDto)
                .collect(Collectors.toList());
    }

    public List<Depense> obtenirDepensesParPeriode(Long utilisateurId, LocalDate dateDebut, LocalDate dateFin) {
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurParId(utilisateurId);
        return depenseRepository.findByUtilisateurAndDateDepenseBetween(utilisateur, dateDebut, dateFin);
    }

    public List<Depense> obtenirDepensesParCategorie(Long utilisateurId, String categorie) {
        Utilisateur utilisateur = utilisateurService.obtenirUtilisateurParId(utilisateurId);
        return depenseRepository.findByUtilisateurAndCategorie(utilisateur, categorie);
    }

    public Depense modifierDepense(Long depenseId, DepenseDto depenseDto, Long utilisateurId) {
        Depense depense = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new RuntimeException("Dépense non trouvée"));

        // Vérifier que la dépense appartient à l'utilisateur
        if (!depense.getUtilisateur().getId().equals(utilisateurId)) {
            throw new RuntimeException("Accès non autorisé à cette dépense");
        }

        depense.setDescription(depenseDto.getDescription());
        depense.setMontant(depenseDto.getMontant());
        depense.setCategorie(depenseDto.getCategorie());
        depense.setDateDepense(depenseDto.getDateDepense());
        depense.setCommentaires(depenseDto.getCommentaires());

        return depenseRepository.save(depense);
    }

    public void supprimerDepense(Long depenseId, Long utilisateurId) {
        Depense depense = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new RuntimeException("Dépense non trouvée"));

        // Vérifier que la dépense appartient à l'utilisateur
        if (!depense.getUtilisateur().getId().equals(utilisateurId)) {
            throw new RuntimeException("Accès non autorisé à cette dépense");
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
        dto.setCommentaires(depense.getCommentaires());
        dto.setUtilisateurId(depense.getUtilisateur().getId());
        return dto;
    }
}
