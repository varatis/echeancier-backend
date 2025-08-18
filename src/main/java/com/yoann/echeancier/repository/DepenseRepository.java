package com.yoann.echeancier.repository;

import com.yoann.echeancier.model.Depense;
import com.yoann.echeancier.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {

    // Trouve toutes les dépenses d'un utilisateur, triées par date de dépense (la plus récente en premier)
    List<Depense> findByUtilisateurOrderByDateDepenseDesc(Utilisateur utilisateur);

    // Trouve les dépenses d'un utilisateur sur une période donnée
    List<Depense> findByUtilisateurAndDateDepenseBetween(
            Utilisateur utilisateur,
            LocalDate dateDebut,
            LocalDate dateFin
    );

    // Trouve les dépenses d'un utilisateur par catégorie
    List<Depense> findByUtilisateurAndCategorie(Utilisateur utilisateur, String categorie);

    // Calcule le total des dépenses d'un utilisateur
    @Query("SELECT SUM(d.montant) FROM Depense d WHERE d.utilisateur = :utilisateur")
    BigDecimal getTotalDepensesByUtilisateur(@Param("utilisateur") Utilisateur utilisateur);

    // Calcule le total des dépenses d'un utilisateur sur une période donnée
    @Query("SELECT SUM(d.montant) FROM Depense d WHERE d.utilisateur = :utilisateur AND d.dateDepense BETWEEN :dateDebut AND :dateFin")
    BigDecimal getTotalDepensesByUtilisateurEtPeriode(
            @Param("utilisateur") Utilisateur utilisateur,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    // Récupère les dépenses par catégorie pour un utilisateur
    @Query("SELECT d.categorie, SUM(d.montant) FROM Depense d WHERE d.utilisateur = :utilisateur GROUP BY d.categorie")
    List<Object[]> getDepensesParCategorie(@Param("utilisateur") Utilisateur utilisateur);

    // Compte le nombre de dépenses pour un utilisateur
    @Query("SELECT COUNT(d) FROM Depense d WHERE d.utilisateur = :utilisateur")
    Long countDepensesByUtilisateur(@Param("utilisateur") Utilisateur utilisateur);
}
